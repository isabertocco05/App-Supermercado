package com.example.avparcial

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class criar_conta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_conta)

        // ROOTLAYOUT
        val rootLayout = findViewById<ConstraintLayout>(R.id.rootLayout)

        // WINDOW INSETS
        ViewCompat.setOnApplyWindowInsetsListener(rootLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonCrieSuaConta = findViewById<Button>(R.id.buttonCrieSuaConta)
        val textViewCrieSuaContaNomeUsuario = findViewById<EditText>(R.id.textViewCrieSuaContaNomeUsuario)
        val textViewCrieSuaContaEmailUsuario = findViewById<EditText>(R.id.textViewCrieSuaContaEmailUsuario)
        val textViewCrieSuaContaSenhaUsuario = findViewById<EditText>(R.id.textViewCrieSuaContaSenhaUsuario)
        val textViewCrieSuaContaConfirmeSenhaUsuario = findViewById<EditText>(R.id.textViewCrieSuaContaConfirmeSenhaUsuario)

        // BOTÃO CRIAR CONTA
        buttonCrieSuaConta.setOnClickListener {
            val nome = textViewCrieSuaContaNomeUsuario.text.toString().trim()
            val email = textViewCrieSuaContaEmailUsuario.text.toString().trim()
            val senha = textViewCrieSuaContaSenhaUsuario.text.toString().trim()
            val senhaConfirmar = textViewCrieSuaContaConfirmeSenhaUsuario.text.toString().trim()

            // VERIFICA EMAIL E SENHA
            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || senhaConfirmar.isEmpty()) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Todos os campos devem ser preenchidos.",
                    Snackbar.LENGTH_LONG
                ).show()
            } else if (!isValidName(nome)) {
                Snackbar.make(findViewById(android.R.id.content), "O nome deve conter letras diferentes e não pode ser apenas números.", Snackbar.LENGTH_LONG).show()
            } else if (!isValidEmail(email)) {
                Snackbar.make(findViewById(android.R.id.content), "Email inválido.", Snackbar.LENGTH_LONG).show()
            } else if (!isValidPassword(senha)) {
                Snackbar.make(findViewById(android.R.id.content), "A senha deve ter pelo menos 8 dígitos e conter, pelo menos, uma letra, um número e um caractere especial.", Snackbar.LENGTH_LONG).show()
            } else if (!isPasswordConfirmed(senha, senhaConfirmar)) {
                Snackbar.make(findViewById(android.R.id.content), "As senhas não são iguais.", Snackbar.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, SuasListas::class.java)
                startActivity(intent)
            }
        }
    }

    // VERIFICA NOME
    private fun isValidName(name: String): Boolean {
        val hasLetters = name.any { it.isLetter() } // Verifica se há pelo menos uma letra
        val hasUniqueCharacters = name.toSet().size > 1 // Verifica se há mais de um caractere diferente
        val hasOnlyLetters = name.all { it.isLetter() } // Verifica se contém apenas letras

        return hasLetters && hasUniqueCharacters && hasOnlyLetters
    }

    // VERIFICA EMAIL
    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                isEmailLengthValid(email)
    }

    private fun isEmailLengthValid(email: String): Boolean {
        return email.length in 3..254
    }

    // VERIFICA SENHA
    private fun isValidPassword(password: String): Boolean {
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }
        return hasLetter && hasDigit && hasSpecialChar && (password.length >= 8)
    }

    // SENHA E CONFIRMACAO SAO IGUAIS
    private fun isPasswordConfirmed(senha: String, senhaConfirmar: String): Boolean {
        return senha == senhaConfirmar
    }
}
