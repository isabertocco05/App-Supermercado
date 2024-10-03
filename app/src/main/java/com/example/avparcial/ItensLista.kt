package com.example.avparcial

import Lista
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.avparcial.databinding.ActivityItensListaBinding

class ItensLista : AppCompatActivity() {

    private lateinit var binding: ActivityItensListaBinding
    private var itensLista: MutableList<Itens> = mutableListOf()
    private var listaFiltrada: MutableList<Itens> = mutableListOf()
    private lateinit var listaPertencente: Lista
    private lateinit var adapter: AdapterItens

    private val addItemLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            data?.getParcelableArrayListExtra<Itens>("novo_item")?.let {
                itensLista.addAll(it)
                listaPertencente.itens_da_lista = ArrayList(itensLista)
                filterList("")
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

        listaPertencente = intent.getParcelableExtra("lista")!!
        binding.tituloNomeLista.setText(listaPertencente.nome.uppercase())


        val novo_item = intent.getParcelableArrayListExtra<Itens>("novo_item")
        novo_item?.let {
            itensLista.addAll(it)
        }

        if (listaPertencente.itens_da_lista != null) {
            itensLista.addAll(listaPertencente.itens_da_lista!!)
        }

        listaFiltrada.addAll(itensLista)

        // PESQUISA
        adapter = AdapterItens(listaFiltrada, ::onClick, ::onDeleteClicked)

        binding.recyclerViewItens.layoutManager = GridLayoutManager(this, 1)
        binding.recyclerViewItens.adapter = adapter

        // Configura o listener de pesquisa
        binding.pesquisa.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText ?: "")
                return true
            }
        })

        binding.FABItens.setOnClickListener {
            val intent = Intent(this, AddItens::class.java)
            addItemLauncher.launch(intent)
        }

        binding.logout2.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.salvar.setOnClickListener{

            listaPertencente.itens_da_lista = ArrayList(itensLista)
            val intent =  Intent(this, SuasListas::class.java).apply{
                putParcelableArrayListExtra("itens_lista", java.util.ArrayList(itensLista))
            }
            setResult(RESULT_OK, intent)
            startActivity(intent)
        }

    }

    private fun filterList(searchText: String) {
        listaFiltrada.clear()
        if (searchText.isEmpty()) {
            listaFiltrada.addAll(itensLista)
        } else {
            itensLista.forEach { item ->
                if (item.nome_item.contains(searchText, ignoreCase = true)) {
                    listaFiltrada.add(item)
                }
            }
        }
        ordenaItens()
        adapter.notifyDataSetChanged()
    }

    private fun onClick(item: Itens) {
        // Não faz nada
    }

    private fun onDeleteClicked(item: Itens) {
        itensLista.remove(item)
        filterList("")
        adapter.notifyDataSetChanged()
    }

    private fun ordenaItens(){
        listaFiltrada.sortWith(compareBy( {it.checked }))
        listaFiltrada.sortWith(compareBy ({ it.categoria } , {it.nome_item}) )
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
            listaPertencente.itens_da_lista = ArrayList(itensLista)
    }


}
