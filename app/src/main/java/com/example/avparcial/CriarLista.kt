package com.example.avparcial

import Lista
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.avparcial.databinding.ActivityCriarListaBinding
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList

class CriarLista : AppCompatActivity() {
    private lateinit var binding: ActivityCriarListaBinding
    private lateinit var nova_lista: List<Lista>
    private var imgUri: Uri? = null

    private val requestPermissionLaucher =
        registerForActivityResult(ActivityResultContracts.RequestPermission())  { isGranted: Boolean ->
            onPermissionResult(isGranted)
        }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { image ->
            imgUri = image
            showImage(imgUri)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCriarListaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        showImage()

        binding.FABFoto.setOnClickListener {
            selectImage()
        }
        binding.buttonCriarLista.setOnClickListener {

            val nome_lista = binding.nomeDaLista.text.toString()
            val imagem_lista = imgUri.toString()
            val id= 1

            nova_lista = listOf(Lista( id.toString(), "$nome_lista", imagem_lista, null ))

            val intent = Intent().apply {
                putParcelableArrayListExtra("nova_lista", ArrayList(nova_lista))
            }
            setResult(RESULT_OK, intent)
            finish()

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

    private fun showImage(image: Uri? = null) {
        Glide.with(this)
            .load(image)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(binding.imagemLista)
    }

}