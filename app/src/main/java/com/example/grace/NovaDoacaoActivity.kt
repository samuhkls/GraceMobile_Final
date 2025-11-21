package com.example.grace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

class NovaDoacaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_doacao)
        val categorias = listOf(
            getString(R.string.categoria_alimentos),
            getString(R.string.categoria_roupas),
            getString(R.string.categoria_higiene),
            getString(R.string.categoria_brinquedos),
            getString(R.string.categoria_livros)
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categorias)

        val actvCategoria = findViewById<AutoCompleteTextView>(R.id.actvCategoria)

        actvCategoria.setAdapter(adapter)

        // (A lógica do botão btnRegistrarDoacao virá aqui depois)
    }
}