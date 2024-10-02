package com.example.avparcial

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avparcial.databinding.ActivityMainBinding
import com.example.avparcial.databinding.ActivityTesteBinding

class activity_teste : AppCompatActivity() {
    private lateinit var binding: ActivityTesteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTesteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.Acessar.setOnClickListener {
            val intent = Intent(this, SuasListas::class.java)
            startActivity(intent)
        }
        binding.Cadastrar.setOnClickListener{
            val intent = Intent(this, criar_conta::class.java)
            startActivity(intent)
        }
    }


}