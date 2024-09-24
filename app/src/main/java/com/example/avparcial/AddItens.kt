package com.example.avparcial

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.avparcial.databinding.ActivityAddItensBinding
import com.example.avparcial.databinding.ActivitySuasListasBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class AddItens : AppCompatActivity() {
    private lateinit var binding: ActivityAddItensBinding
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

        val tipoUnidade = resources.getStringArray(R.array.tipos_unidade)

    }

}