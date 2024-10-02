package com.example.avparcial

import Lista
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.avparcial.databinding.ActivitySuasListasBinding

class SuasListas : AppCompatActivity() {
    private lateinit var binding: ActivitySuasListasBinding
    private var suasListas: MutableList<Lista> = mutableListOf()
    private var listaFiltrada: MutableList<Lista> = mutableListOf()
    private lateinit var adapter: AdapterListas

    private val createListLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val nova_lista = result.data?.getParcelableArrayListExtra<Lista>("nova_lista")
            nova_lista?.let {
                suasListas.addAll(it)
                listaFiltrada.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

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


        // PESQUISA
        binding.pesquisa.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText ?: "")
                return true
            }
        })

        val nova_lista = intent.getParcelableArrayListExtra<Lista>("nova_lista")
        nova_lista?.let {
            suasListas.addAll(it)
            listaFiltrada.addAll(it)
        }

        adapter = AdapterListas(listaFiltrada, ::onListClicked)
        binding.recyclerViewListas.adapter = adapter
        binding.recyclerViewListas.layoutManager = GridLayoutManager(this, 2)

        binding.FAB.setOnClickListener {
            val intent = Intent(this, CriarLista::class.java)
            createListLauncher.launch(intent)
        }

        binding.logout.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.FABDelete.setOnClickListener{
            adapter.checkDeleteMode()
        }
    }

    private fun filterList(searchText: String) {
        listaFiltrada.clear()
        if (searchText.isEmpty()) {
            listaFiltrada.addAll(suasListas)
        } else {
            suasListas.forEach { lista ->
                if (lista.nome.contains(searchText, ignoreCase = true)) {
                    listaFiltrada.add(lista)
                }
            }
        }
        adapter.notifyDataSetChanged() // Atualiza o RecyclerView
    }

    private fun onListClicked(lista: Lista) {
        Toast.makeText(this, lista.toString(), Toast.LENGTH_LONG).show()

    }
}
