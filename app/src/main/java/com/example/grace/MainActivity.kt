package com.example.grace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.adapter.CategoriaAdapter
import com.example.grace.model.Categoria

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. RECEBER DADOS DO LOGIN
        val nomeUsuario = intent.getStringExtra("USER_NAME") ?: "Convidado"
        // Pegamos o ID. Se não vier nada, usamos -1 (que indica erro)
        val userId = intent.getLongExtra("USER_ID", -1)

        // Configura Saudação
        val tvSaudacao = findViewById<TextView>(R.id.tvSaudacao)
        val textoSaudacao = getString(R.string.saudacao_ola) + " " + nomeUsuario
        tvSaudacao.text = textoSaudacao

        // 2. CONFIGURAR RECYCLERVIEW (Igual ao que já estava)
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

        // 3. BOTÕES
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

        // --- BOTÃO NOVA DOAÇÃO (AQUI ESTÁ A MÁGICA) ---
        val btnNovaDoacao = findViewById<Button>(R.id.btnAcaoPrincipal)
        btnNovaDoacao.setOnClickListener {
            val intent = Intent(this, NovaDoacaoActivity::class.java)

            // REPASSA O ID PARA A PRÓXIMA TELA
            intent.putExtra("USER_ID", userId)

            startActivity(intent)
        }
    }
}