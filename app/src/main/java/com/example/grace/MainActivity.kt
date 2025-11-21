package com.example.grace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.adapter.CategoriaAdapter
import com.example.grace.model.Categoria

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvSaudacao = findViewById<TextView>(R.id.tvSaudacao)
        val nomeUsuario = intent.getStringExtra("USER_NAME") ?: "Convidado"
        val textoSaudacao = getString(R.string.saudacao_ola) + " " + nomeUsuario
        tvSaudacao.text = textoSaudacao

        val rvCategorias = findViewById<RecyclerView>(R.id.rvCategorias)

        val categorias = listOf(
            Categoria(getString(R.string.categoria_higiene), R.drawable.ic_hygiene),
            Categoria(getString(R.string.categoria_alimentos), R.drawable.ic_food),
            Categoria(getString(R.string.categoria_roupas), R.drawable.ic_clothes),
            Categoria(getString(R.string.categoria_brinquedos), R.drawable.ic_toys),
            Categoria(getString(R.string.categoria_livros), R.drawable.ic_books)
        )
        rvCategorias.layoutManager = GridLayoutManager(this, 2)
        rvCategorias.adapter = CategoriaAdapter(categorias)

        val ivPerfil1 = findViewById<android.widget.ImageView>(R.id.ivPerfil1)
        ivPerfil1.setOnClickListener {
            val intent = Intent(this, SobreActivity::class.java)
            startActivity(intent)
        }

        val ivPerfil2 = findViewById<android.widget.ImageView>(R.id.ivPerfil2)
        ivPerfil2.setOnClickListener {
            val intent = Intent(this, ContatoActivity::class.java)
            startActivity(intent)
        }

        val btnNovaDoacao = findViewById<android.widget.Button>(R.id.btnAcaoPrincipal)
        btnNovaDoacao.setOnClickListener {
            val intent = Intent(this, NovaDoacaoActivity::class.java)
            startActivity(intent)
        }
    }
}