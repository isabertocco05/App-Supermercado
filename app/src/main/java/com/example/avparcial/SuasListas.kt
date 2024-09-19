package com.example.avparcial

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

        var suasListas = mutableListOf(
            Lista ("Hortifruti", "")
        )

        val adapter= AdapterListas(suasListas, ::onListClicked)
        val layoutManager = GridLayoutManager(this, 2)

        binding.recyclerViewListas.adapter = adapter
        binding.recyclerViewListas.layoutManager = layoutManager

        binding.FAB.setOnClickListener {
            val newList = Lista("Mais uma Lista", "")
            suasListas.add(newList)
            adapter.notifyItemInserted(suasListas.size - 1)
        }
    }

    private fun onListClicked(lista: Lista){
        Toast.makeText(this, lista.toString(), Toast.LENGTH_LONG).show()
    }
}