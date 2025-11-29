package com.example.grace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.adapter.NecessidadeAdapter
import com.example.grace.model.Necessidade
import com.example.grace.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MuralNecessidadesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta) // Reutilizando layout

        // Muda o título da tela
        val tvTitulo = findViewById<TextView>(R.id.tvTituloConsulta)
        tvTitulo.text = "Mural de Necessidades"

        // escondendo o botao de +
        val fab = findViewById<com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton>(R.id.fabSolicitarAlgo)
        fab.visibility = android.view.View.GONE

        val rv = findViewById<RecyclerView>(R.id.rvProdutos)
        rv.layoutManager = LinearLayoutManager(this)

        carregarNecessidades(rv)
    }

    private fun carregarNecessidades(rv: RecyclerView) {
        RetrofitClient.apiService.listarNecessidades().enqueue(object : Callback<List<Necessidade>> {
            override fun onResponse(call: Call<List<Necessidade>>, response: Response<List<Necessidade>>) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    rv.adapter = NecessidadeAdapter(lista)

                    if (lista.isEmpty()) {
                        Toast.makeText(this@MuralNecessidadesActivity, "Nenhum pedido pendente.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<Necessidade>>, t: Throwable) {
                Toast.makeText(this@MuralNecessidadesActivity, "Erro ao carregar.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}