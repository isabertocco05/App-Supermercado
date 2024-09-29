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
//    private val requestPermissionLaucher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission())  { isGranted: Boolean ->
//            onPermissionResult(isGranted)
//        }

//    private val getContent =
//        registerForActivityResult(ActivityResultContracts.GetContent()) { image ->
//            showImage(image)
//        }

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

//        showImage()

//        binding.buttonCriarConta.setOnClickListener {
//            selectImage()
//        }

        binding.buttonAcessar.setOnClickListener {
            val email = binding.emailusuario.text.toString().trim()
            val senha = binding.SenhaUsuario.text.toString().trim()

            // Verificações de email e senha
            if (email.isEmpty() || senha.isEmpty()) {
                Snackbar.make(findViewById(android.R.id.content), "Todos os campos devem ser preenchidos.", Snackbar.LENGTH_LONG).show()
            } else if (!isValidEmail(email)) {
                Snackbar.make(findViewById(android.R.id.content), "Email inválido.", Snackbar.LENGTH_LONG).show()
            } else if (!isValidPassword(senha)) {
                Snackbar.make(findViewById(android.R.id.content), "A senha deve conter pelo menos uma letra, um número e um caractere especial.", Snackbar.LENGTH_LONG).show()
            } else {
                // ok, navegue para a próxima tela
                val intent = Intent(this, SuasListas::class.java)
                startActivity(intent)
            }
        }
        binding.buttonCriarConta.setOnClickListener {
            val intent = Intent(this, criar_conta::class.java)
            startActivity(intent)
        }

    }

//    private fun selectImage(){
//
//        // já está com acesso permitido?
//        when{
//            //sim
//            ContextCompat.checkSelfPermission(
//                this, READ_MEDIA_IMAGES
//            ) == PackageManager.PERMISSION_GRANTED -> {
//                getContent.launch("image/*")
//            }
//            // não
//            else -> {
//                requestPermissionLaucher.launch(READ_MEDIA_IMAGES)
//            }
//        }
//
//    }

//    private fun onPermissionResult(isGranted: Boolean){
//        // está garantido? sim
//        if (isGranted){
//            getContent.launch("image/*")
//        }
//        //não
//        else{
//            //mensagem explicando o porque é necessário o uso desse recurso
//            showPermissionExplanation(requestAgain = false)
//        }
//    }

//    private fun showPermissionExplanation(requestAgain: Boolean){
//        val snackbar = Snackbar.make(
//            findViewById(android.R.id.content),
//            "Precisamos de sua autorização para selecionar a imagem da galeria",
//            Snackbar.LENGTH_LONG
//        )
//        if (requestAgain){
//            snackbar.setAction("Permitir"){
//                requestPermissionLaucher.launch(READ_MEDIA_IMAGES)
//            }
//        }
//    }

//    private fun showImage(image: Any? = null) {
//        Glide.with(this)
//            .load(image)
//            .centerCrop()
//            .placeholder(R.drawable.placeholder)
//            .into(binding.imgSelected)
//    }
// VERIFICA EMAIL
private fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
            isEmailLengthValid(email) &&
            !isTemporaryEmail(email) // Adicione ou remova verificações conforme necessário
}

    private fun isEmailLengthValid(email: String): Boolean {
        return email.length in 3..254
    }

    private fun isTemporaryEmail(email: String): Boolean {
        val temporaryDomains = listOf("tempmail.com", "mailinator.com")
        val domain = email.substringAfter("@")
        return domain in temporaryDomains
    }

    // VERIFICA SENHA
    private fun isValidPassword(password: String): Boolean {
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }
        return hasLetter && hasDigit && hasSpecialChar && (password.length >= 8)
    }


}
