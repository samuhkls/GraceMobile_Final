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


        val nomeUsuario = intent.getStringExtra("USER_NAME") ?: "Convidado"
        val userId = intent.getLongExtra("USER_ID", -1)
        val tipoUsuario = intent.getStringExtra("USER_TYPE") ?: "Receptor"


        val tvSaudacao = findViewById<TextView>(R.id.tvSaudacao)
        tvSaudacao.text = getString(R.string.saudacao_ola) + " " + nomeUsuario


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
        val ivPerfil = findViewById<ImageView>(R.id.ivPerfil)

        ivPerfil.setOnClickListener { view ->

            val popup = android.widget.PopupMenu(this, view)

            popup.menu.add(0, 1, 0, getString(R.string.titulo_sobre_nos))
            popup.menu.add(0, 2, 0, getString(R.string.titulo_duvidas))
            popup.menu.add(0, 3, 0, "Sair / Logout")

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    1 -> {
                        startActivity(Intent(this, SobreActivity::class.java))
                        true
                    }
                    2 -> {
                        startActivity(Intent(this, ContatoActivity::class.java))
                        true
                    }
                    3 -> {
                        realizarLogout()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

        val btnNovaDoacao = findViewById<Button>(R.id.btnAcaoPrincipal)
        val btnVerNecessidades = findViewById<Button>(R.id.btnVerNecessidades)


        if (tipoUsuario.equals("Doador", ignoreCase = true) || tipoUsuario.equals("Admin", ignoreCase = true)) {

            btnNovaDoacao.visibility = View.VISIBLE
            btnNovaDoacao.setOnClickListener {
                val intent = Intent(this, NovaDoacaoActivity::class.java)
                intent.putExtra("USER_ID", userId)
                startActivity(intent)
            }

            btnVerNecessidades.visibility = View.VISIBLE
            btnVerNecessidades.setOnClickListener {
                startActivity(Intent(this, MuralNecessidadesActivity::class.java))
            }

        } else {

            btnNovaDoacao.visibility = View.GONE
            btnVerNecessidades.visibility = View.GONE
        }
    }
    private fun realizarLogout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
        finish()
    }
}