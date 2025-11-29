package com.example.grace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        val userId = intent.getLongExtra("USER_ID", -1)
        val tipoUsuario = intent.getStringExtra("USER_TYPE") ?: "Receptor"

        // Configura Saudação
        val tvSaudacao = findViewById<TextView>(R.id.tvSaudacao)
        tvSaudacao.text = getString(R.string.saudacao_ola) + " " + nomeUsuario

        // 2. CONFIGURAR RECYCLERVIEW
        val rvCategorias = findViewById<RecyclerView>(R.id.rvCategorias)
        val categorias = listOf(
            Categoria(getString(R.string.categoria_higiene), R.drawable.ic_hygiene),
            Categoria(getString(R.string.categoria_alimentos), R.drawable.ic_food),
            Categoria(getString(R.string.categoria_roupas), R.drawable.ic_clothes),
            Categoria(getString(R.string.categoria_brinquedos), R.drawable.ic_toys),
            Categoria(getString(R.string.categoria_livros), R.drawable.ic_books)
        )

        rvCategorias.layoutManager = GridLayoutManager(this, 2)
        rvCategorias.adapter = CategoriaAdapter(categorias) { categoria ->
            val intent = Intent(this, ConsultaActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }

        // 3. BOTÕES DE PERFIL (Mantive sua lógica funcional)


        // --- 4. BOTÕES DE AÇÃO (Lógica Unificada) ---

        val btnNovaDoacao = findViewById<Button>(R.id.btnAcaoPrincipal)
        val btnVerNecessidades = findViewById<Button>(R.id.btnVerNecessidades)

        // Lógica para DOADORES e ADMINS
        if (tipoUsuario.equals("Doador", ignoreCase = true) || tipoUsuario.equals("Admin", ignoreCase = true)) {

            // Mostra o botão "Nova Doação"
            btnNovaDoacao.visibility = View.VISIBLE
            btnNovaDoacao.setOnClickListener {
                val intent = Intent(this, NovaDoacaoActivity::class.java)
                intent.putExtra("USER_ID", userId)
                startActivity(intent)
            }

            // Mostra o botão "Ver Mural"
            btnVerNecessidades.visibility = View.VISIBLE
            btnVerNecessidades.setOnClickListener {
                startActivity(Intent(this, MuralNecessidadesActivity::class.java))
            }

        } else {
            // Se for Receptor, esconde ambos (ele usa o FAB na tela de Consulta)
            btnNovaDoacao.visibility = View.GONE
            btnVerNecessidades.visibility = View.GONE
        }
    }
}