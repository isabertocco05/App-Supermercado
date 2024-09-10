package com.example.avparcial

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avparcial.databinding.ActivityListasBinding

class Listas : AppCompatActivity() {
    private lateinit var binding: ActivityListasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        LISTA DINAMICA
//        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray)
//        val listView: ListView = findViewById(R.id.listview)
//        listView.adapter = adapter


        binding = ActivityListasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addLista.setOnClickListener{
            val intent = Intent (this,AddLista::class.java)
            startActivity(intent)
        }
    }
}