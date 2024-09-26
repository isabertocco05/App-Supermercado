package com.example.avparcial

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avparcial.databinding.ActivityAddItensBinding
import com.google.android.material.textfield.TextInputEditText

class AddItens : AppCompatActivity() {
    private lateinit var binding: ActivityAddItensBinding
    private var itemList = ArrayList<Itens>()

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

            val nome_item: TextInputEditText = binding.nomeDoItem
            val quantidade: TextInputEditText = binding.quantidade
            val und: AutoCompleteTextView = binding.tipoUnid
            val cat: AutoCompleteTextView = binding.tipoCat

            itemList.add(Itens(0, "$nome_item", 0, "$und", "$cat"))


//            }
        }

    }

}