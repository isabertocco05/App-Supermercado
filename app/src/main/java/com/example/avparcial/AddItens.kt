package com.example.avparcial

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avparcial.databinding.ActivityAddItensBinding
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList

class AddItens : AppCompatActivity() {
    private lateinit var binding: ActivityAddItensBinding
    private lateinit var novo_item: List<Itens>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddItensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val autoCompleteuUnidade =  binding.tipoUnid
        val tipoUnidade = listOf("Un", "Kg", "Grama")
        val adapterUn = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, tipoUnidade)
        autoCompleteuUnidade.setAdapter(adapterUn)

        val tipoCat = listOf(
            Categoria(R.mipmap.ic_fruta, "Fruta"),
            Categoria(R.mipmap.ic_legumes, "Legumes e Vegetais"),
            Categoria(R.mipmap.ic_comida, "Comida")
        )
        val adapterCat = CategoriaAdapter(this, tipoCat)
        binding.tipoCat.setAdapter(adapterCat)

        binding.addItem.setOnClickListener{

            val nome_item = binding.nomeDoItem.text.toString()
            val quantidade = binding.quantidade.text.toString()
            val und = binding.tipoUnid.text.toString()
            val cat = binding.tipoCat.toString()

            novo_item = listOf(Itens(0, "$nome_item", "$quantidade", "$und", "$cat"))

            if (nome_item.isNullOrBlank() || quantidade.isNullOrBlank() || und.isNullOrBlank() || cat.isNullOrBlank()) {
                Snackbar.make(findViewById(android.R.id.content), "Todos os campos devem ser preenchidos.", Snackbar.LENGTH_LONG).show()
            } else if (!isValidName(nome_item.trim())) {
                Snackbar.make(findViewById(android.R.id.content), "O nome deve conter letras diferentes e não pode ser apenas números.", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Item adicionado", Snackbar.LENGTH_SHORT).show()

                val intent = Intent().apply {
                    putParcelableArrayListExtra("novo_item", ArrayList(novo_item))
                }
                setResult(RESULT_OK, intent)
                finish()
            }

        }
    }

    // VERIFICA NOME
    private fun isValidName(name: String): Boolean {
        val hasLetters = name.any { it.isLetter() }
        val hasUniqueCharacters = name.toSet().size > 1 // Verifica caractere diferente
        val hasOnlyLetters = name.all { it.isLetter() } // Verifica se contém apenas letras

        return hasLetters && hasUniqueCharacters && hasOnlyLetters
    }

}