package com.example.avparcial

import android.Manifest.permission.READ_MEDIA_IMAGES
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.avparcial.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // foram criados requestPermissionLaucher e onPermissionResult
    private val requestPermissionLaucher =
        registerForActivityResult(ActivityResultContracts.RequestPermission())  { isGranted: Boolean ->
            onPermissionResult(isGranted)
        }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { image ->
            showImage(image)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        showImage()

        binding.buttonCriarConta.setOnClickListener {
            selectImage()
        }

        binding.buttonAcessar.setOnClickListener {
            val intent = Intent(this, SuasListas::class.java)
            startActivity(intent)
        }
    }

    private fun selectImage(){

        // já está com acesso permitido?
        when{
            //sim
            ContextCompat.checkSelfPermission(
                this, READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED -> {
                getContent.launch("image/*")
            }
            // não
            else -> {
                requestPermissionLaucher.launch(READ_MEDIA_IMAGES)
            }
        }

    }

    private fun onPermissionResult(isGranted: Boolean){
        // está garantido? sim
        if (isGranted){
            getContent.launch("image/*")
        }
        //não
        else{
            //mensagem explicando o porque é necessário o uso desse recurso
            showPermissionExplanation(requestAgain = false)
        }
    }

    private fun showPermissionExplanation(requestAgain: Boolean){
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            "Precisamos de sua autorização para selecionar a imagem da galeria",
            Snackbar.LENGTH_LONG
        )
        if (requestAgain){
            snackbar.setAction("Permitir"){
                requestPermissionLaucher.launch(READ_MEDIA_IMAGES)
            }
        }
    }

    private fun showImage(image: Any? = null) {
        Glide.with(this)
            .load(image)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(binding.imgSelected)
    }


}
