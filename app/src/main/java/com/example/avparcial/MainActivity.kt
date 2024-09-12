package com.example.avparcial

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.avparcial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imgLauncher: ActivityResultLauncher<Intent>
    private val constReqImage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // inicializa a activity launcher para selecionar a imagem
        imgLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val imgURI: Uri? = result.data?.data
                    Glide.with(this)
                        .load(imgURI)
                        .centerCrop()
                        .into(binding.imgSelected)
                }
            }

        binding.btnSelectImage.setOnClickListener {
            Log.d("MainActivity", "Solicitando permissão de armazenamento")
            // verifica se a permissao já foi concedida
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), constReqImage
                )

            } else {
                abrirGaleria()
            }

        }

        binding.acessar.setOnClickListener {
            val intent = Intent(this, Listas::class.java)
            startActivity(intent)
        }

    }
        private fun abrirGaleria() {
            Log.d("MainActivity", "Solicitando permissão de armazenamento")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imgLauncher.launch(intent)
        }

    // precisamos sobreescrever esse método pq o usuario respondeu a uma solicitação
        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("MainActivity", "Permissão concedida: ${grantResults[0] == PackageManager.PERMISSION_GRANTED}")

        if (requestCode == constReqImage) {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    abrirGaleria()
                }
                else Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show()
            }


        }
}
