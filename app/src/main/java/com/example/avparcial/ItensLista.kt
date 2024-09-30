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
    private lateinit var itensLista: MutableList<Itens>

    private val addItemLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            data?.getParcelableArrayListExtra<Itens>("novo_item")?.let { newItems ->
                itensLista.clear()
                itensLista.addAll(newItems)
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

        itensLista = mutableListOf()

        val adapter = AdapterItens(itensLista, ::onItensClicked)
        val layoutManager = LinearLayoutManager(this )

        binding.recyclerViewItens.adapter = adapter
        binding.recyclerViewItens.layoutManager = layoutManager

        binding.FABItens.setOnClickListener {
            val intent = Intent(binding.root.context, AddItens::class.java)
            binding.root.context.startActivity(intent)
//            val newItem = Itens("novo item", 0, "un", "comida")
//            itensLista.add(newItem)
//            adapter.notifyItemInserted(itensLista.size - 1)
            intent.putParcelableArrayListExtra("novo_item", ArrayList(itensLista))
            addItemLauncher.launch(intent)
        }
    }

    private fun onItensClicked(itens: Itens){
        Toast.makeText(this, itens.toString(), Toast.LENGTH_LONG).show()
    }

}