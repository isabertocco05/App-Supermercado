package com.example.avparcial

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avparcial.databinding.ActivityTesteBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTesteBinding

    // Email e senha fixos
    private val emailLogin = "parcial@unaerp.com"
    private val senhaLogin = "Aplicativo123@"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTesteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajustes nas barras de sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Clique no botão "Acessar"
        binding.Acessar.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val senha = binding.senha.text.toString().trim()

            // Verificações de email e senha fixos
            if (email.isEmpty() || senha.isEmpty()) {
                Snackbar.make(findViewById(android.R.id.content), "Todos os campos devem ser preenchidos.", Snackbar.LENGTH_LONG).show()
            } else if (!verificaEmail(email)) {
                Snackbar.make(findViewById(android.R.id.content), "Email inválido.", Snackbar.LENGTH_LONG).show()
            } else if (email != emailLogin) {
                Snackbar.make(findViewById(android.R.id.content), "Email não cadastrado.", Snackbar.LENGTH_LONG).show()
            } else if (senha != senhaLogin) {
                Snackbar.make(findViewById(android.R.id.content), "Senha incorreta.", Snackbar.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, SuasListas::class.java)
                startActivity(intent)
            }
        }

        // Clique no botão "Cadastrar"
        binding.Cadastrar.setOnClickListener {
            val intent = Intent(this, criar_conta::class.java)
            startActivity(intent)
        }
    }

    // Verifica se o email é válido
    private fun verificaEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.length in 3..254
    }

    private fun verificaTamanhoEmail(email: String): Boolean {
        return email.length in 3..254
    }


    }

