package com.example.avparcial

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avparcial.databinding.ActivityItensListaBinding

class ItensLista : AppCompatActivity() {
    private lateinit var binding: ActivityItensListaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityItensListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var itensLista = mutableListOf(
            Itens ("maçã", 4, "un", "fruta")
        )

        val adapter = AdapterItens(itensLista, ::onItensClicked)
        val layoutManager = LinearLayoutManager(this)

        binding.recyclerViewItens.adapter = adapter
        binding.recyclerViewItens.layoutManager = layoutManager

        binding.FABItens.setOnClickListener {
            val newItem = Itens("novo item", 0, "un", "comida")
            itensLista.add(newItem)
            adapter.notifyItemInserted(itensLista.size - 1)
        }
    }

    private fun onItensClicked(itens: Itens){
        Toast.makeText(this, itens.toString(), Toast.LENGTH_LONG).show()
    }

}