package com.example.avparcial

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avparcial.databinding.ActivityCriarContaBinding
import com.google.android.material.snackbar.Snackbar

class criar_conta : AppCompatActivity() {
    private lateinit var binding: ActivityCriarContaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarContaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rootLayout = binding.root // Acesso ao layout raiz
        ViewCompat.setOnApplyWindowInsetsListener(rootLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.CadastrarUsuario.setOnClickListener {
            val nomeUsuario = binding.usuario.text.toString().trim() // Nome de usuário
            val nomeCompleto = binding.nomeComp.text.toString().trim() // Nome completo
            val email = binding.seuEmail.text.toString().trim() // Email
            val senha = binding.senha.text.toString().trim() // Senha
            val senhaConfirmar = binding.confirmacaoSenha.text.toString().trim() // Confirmação da senha

            if (nomeUsuario.isEmpty() || nomeCompleto.isEmpty() || email.isEmpty() || senha.isEmpty() || senhaConfirmar.isEmpty()) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Todos os campos devem ser preenchidos.",
                    Snackbar.LENGTH_LONG
                ).show()
            } else if (!verificaNome(nomeUsuario)) {
                Snackbar.make(findViewById(android.R.id.content), "O nome de usuário deve conter letras diferentes e não pode ser apenas números.", Snackbar.LENGTH_LONG).show()
            } else if (!verificaNome(nomeCompleto)) {
                Snackbar.make(findViewById(android.R.id.content), "O nome completo deve conter letras diferentes e não pode ser números.", Snackbar.LENGTH_LONG).show()
            } else if (!verificaEmail(email)) {
                Snackbar.make(findViewById(android.R.id.content), "Email inválido.", Snackbar.LENGTH_LONG).show()
            } else if (!verificaSenha(senha)) {
                Snackbar.make(findViewById(android.R.id.content), "A senha deve ter pelo menos 8 dígitos e conter, pelo menos, uma letra, um número e um caractere especial.", Snackbar.LENGTH_LONG).show()
            } else if (!verificaConfirmacao(senha, senhaConfirmar)) {
                Snackbar.make(findViewById(android.R.id.content), "As senhas não são iguais.", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Usuário criado com sucesso", Snackbar.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun verificaNome(name: String): Boolean {
        val hasLetters = name.any { it.isLetter() }
        val hasUniqueCharacters = name.toSet().size > 1
        val hasOnlyLettersAndSpaces = name.all { it.isLetter() || it.isWhitespace() }
        return hasLetters && hasUniqueCharacters && hasOnlyLettersAndSpaces && name.isNotBlank()
    }

    private fun verificaEmail(email: String): Boolean {
        return email.contains("@") &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                verificaTamanhoEmail(email)
    }

    private fun verificaTamanhoEmail(email: String): Boolean {
        return email.length in 3..254
    }

    private fun verificaSenha(password: String): Boolean {
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }
        return hasLetter && hasDigit && hasSpecialChar && (password.length >= 8)
    }

    private fun verificaConfirmacao(senha: String, senhaConfirmar: String): Boolean {
        return senha == senhaConfirmar
    }
}
