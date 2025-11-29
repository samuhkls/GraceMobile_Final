package com.example.grace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog // Importante para o popup de confirmação
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
        setContentView(R.layout.activity_consulta) // Vamos reutilizar o layout da Consulta por enquanto!

        // Se você criou um layout específico (ex: activity_gerenciar.xml), mude acima.
        // Mas o activity_consulta.xml serve perfeitamente pois só tem uma RecyclerView.

        val fab = findViewById<com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton>(R.id.fabSolicitarAlgo)
        fab.visibility = android.view.View.GONE

        rvGerenciar = findViewById(R.id.rvProdutos) // ID da RecyclerView no layout de consulta
        rvGerenciar.layoutManager = LinearLayoutManager(this)

        // Carrega a lista assim que abre a tela
        carregarDoacoes()
    }

    private fun carregarDoacoes() {
        val call = RetrofitClient.apiService.listarDoacoes()

        call.enqueue(object : Callback<List<Doacao>> {
            override fun onResponse(call: Call<List<Doacao>>, response: Response<List<Doacao>>) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()

                    // Configura o adapter passando a lista E a função de clique (lambda)
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

    // Mostra um popup "Tem certeza?" antes de apagar
    private fun confirmarExclusao(doacao: Doacao) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.desc_botao_excluir)) // "Excluir doação"
            .setMessage(getString(R.string.msg_confirmar_exclusao)) // "Tem certeza...?"
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

                        // Recarrega a lista para a doação sumir da tela
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