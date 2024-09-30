package com.example.avparcial

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avparcial.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
        binding.teste.setOnClickListener{
            val intent = Intent(this, activity_teste::class.java)
            startActivity(intent)
        }

    }

    // VERIFICA EMAIL
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                isEmailLengthValid(email) &&
                !isTemporaryEmail(email)
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
