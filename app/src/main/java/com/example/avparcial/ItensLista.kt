package com.example.avparcial

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avparcial.databinding.ActivityItensListaBinding

class ItensLista : AppCompatActivity() {
    private lateinit var binding: ActivityItensListaBinding
    private var itensLista: MutableList<Itens> = mutableListOf()
    private val addItemLauncher = registerForActivityResult( ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            data?.getParcelableArrayListExtra<Itens>("novo_item")?.let {
                itensLista.addAll(it)
                binding.recyclerViewItens.adapter?.notifyDataSetChanged()
            }
        }
    }


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

        val novo_item = intent.getParcelableArrayListExtra<Itens>("novo_item")
        novo_item?.let{
            itensLista.addAll(it)
        }

        val adapter = AdapterItens(itensLista, ::onItensClicked)
        val layoutManager = LinearLayoutManager(this )

        binding.recyclerViewItens.adapter = adapter
        binding.recyclerViewItens.layoutManager = layoutManager

        binding.FABItens.setOnClickListener {
            val intent = Intent(this, AddItens::class.java)
            addItemLauncher.launch(intent)
        }
    }

    private fun onItensClicked(itens: Itens){
        Toast.makeText(this, itens.toString(), Toast.LENGTH_LONG).show()
    }

}