package com.example.avparcial

import Lista
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.avparcial.databinding.ActivitySuasListasBinding

class SuasListas : AppCompatActivity() {
    private lateinit var binding: ActivitySuasListasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySuasListasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nova_lista = intent.getParcelableArrayListExtra<Lista>("nova_lista") ?: arrayListOf()

        val adapter= AdapterListas(nova_lista as List<Lista>, ::onListClicked)
        val layoutManager = GridLayoutManager(this, 2)

        binding.recyclerViewListas.adapter = adapter
        binding.recyclerViewListas.layoutManager = layoutManager

        adapter.notifyItemInserted(nova_lista.size - 1)

        binding.FAB.setOnClickListener {
            val intent = Intent(binding.root.context, CriarLista::class.java)
            binding.root.context.startActivity(intent)
        }

//        binding.pesquisa.addTextChangedListener { text ->
//            val searchText = text.toString()
//            adapter.search(searchText)
//        }
    }

    private fun onListClicked(lista: Lista){
        Toast.makeText(this, lista.toString(), Toast.LENGTH_LONG).show()
    }
}