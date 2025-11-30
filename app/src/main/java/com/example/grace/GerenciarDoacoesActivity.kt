package com.example.grace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.adapter.GerenciarAdapter
import com.example.grace.model.CadastroResponse
import com.example.grace.model.Doacao
import com.example.grace.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GerenciarDoacoesActivity : AppCompatActivity() {

    private lateinit var rvGerenciar: RecyclerView
    private lateinit var adapter: GerenciarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta)

        val fab = findViewById<com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton>(R.id.fabSolicitarAlgo)
        fab.visibility = android.view.View.GONE

        rvGerenciar = findViewById(R.id.rvProdutos)
        rvGerenciar.layoutManager = LinearLayoutManager(this)

        carregarDoacoes()
    }

    private fun carregarDoacoes() {
        val call = RetrofitClient.apiService.listarDoacoes()

        call.enqueue(object : Callback<List<Doacao>> {
            override fun onResponse(call: Call<List<Doacao>>, response: Response<List<Doacao>>) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()

                    adapter = GerenciarAdapter(lista) { doacaoParaDeletar ->
                        confirmarExclusao(doacaoParaDeletar)
                    }
                    rvGerenciar.adapter = adapter

                    if (lista.isEmpty()) {
                        Toast.makeText(this@GerenciarDoacoesActivity, getString(R.string.label_nenhuma_doacao), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@GerenciarDoacoesActivity, "Erro ao carregar lista", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Doacao>>, t: Throwable) {
                Toast.makeText(this@GerenciarDoacoesActivity, "Erro de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun confirmarExclusao(doacao: Doacao) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.desc_botao_excluir))
            .setMessage(getString(R.string.msg_confirmar_exclusao))
            .setPositiveButton("Sim") { _, _ ->
                deletarDoacaoRealmente(doacao.id)
            }
            .setNegativeButton("Não", null)
            .show()
    }

    private fun deletarDoacaoRealmente(id: Int) {
        val call = RetrofitClient.apiService.deletarDoacao(id)

        call.enqueue(object : Callback<CadastroResponse> {
            override fun onResponse(call: Call<CadastroResponse>, response: Response<CadastroResponse>) {
                if (response.isSuccessful) {
                    val resp = response.body()
                    if (resp?.sucesso == true) {
                        Toast.makeText(this@GerenciarDoacoesActivity, getString(R.string.msg_exclusao_sucesso), Toast.LENGTH_SHORT).show()

                        carregarDoacoes()
                    } else {
                        Toast.makeText(this@GerenciarDoacoesActivity, resp?.mensagem ?: "Erro ao excluir", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<CadastroResponse>, t: Throwable) {
                Toast.makeText(this@GerenciarDoacoesActivity, "Erro de conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }
}