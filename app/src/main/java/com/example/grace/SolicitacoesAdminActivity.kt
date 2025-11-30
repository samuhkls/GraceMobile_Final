package com.example.grace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.adapter.SolicitacoesAdminAdapter
import com.example.grace.model.CadastroResponse
import com.example.grace.model.SolicitacaoAdmin
import com.example.grace.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SolicitacoesAdminActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta)

        val titulo = findViewById<TextView>(R.id.tvTituloConsulta)
        titulo.text = "Gerenciar Entregas"

        val fab = findViewById<com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton>(R.id.fabSolicitarAlgo)
        fab.visibility = android.view.View.GONE

        rv = findViewById(R.id.rvProdutos)
        rv.layoutManager = LinearLayoutManager(this)

        carregarLista()
    }

    private fun carregarLista() {
        RetrofitClient.apiService.listarSolicitacoesAdmin().enqueue(object : Callback<List<SolicitacaoAdmin>> {
            override fun onResponse(call: Call<List<SolicitacaoAdmin>>, response: Response<List<SolicitacaoAdmin>>) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    rv.adapter = SolicitacoesAdminAdapter(lista) { solicitacao ->
                        confirmarEntrega(solicitacao)
                    }

                    if (lista.isEmpty()) {
                        Toast.makeText(this@SolicitacoesAdminActivity, "Nenhuma solicitação pendente.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<List<SolicitacaoAdmin>>, t: Throwable) {
                Toast.makeText(this@SolicitacoesAdminActivity, "Erro conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun confirmarEntrega(solicitacao: SolicitacaoAdmin) {
        AlertDialog.Builder(this)
            .setTitle("Confirmar Entrega")
            .setMessage("Marcar item como entregue para ${solicitacao.nomeReceptor}?")
            .setPositiveButton("Sim, Entregue") { _, _ ->

                RetrofitClient.apiService.marcarComoEntregue(solicitacao.id).enqueue(object : Callback<CadastroResponse> {
                    override fun onResponse(call: Call<CadastroResponse>, response: Response<CadastroResponse>) {
                        if (response.isSuccessful && response.body()?.sucesso == true) {
                            Toast.makeText(this@SolicitacoesAdminActivity, "Entrega confirmada!", Toast.LENGTH_SHORT).show()

                            enviarEmailNotificacao(solicitacao)

                            carregarLista()
                        }
                    }
                    override fun onFailure(call: Call<CadastroResponse>, t: Throwable) {
                        Toast.makeText(this@SolicitacoesAdminActivity, "Erro de conexão", Toast.LENGTH_SHORT).show()
                    }
                })

            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    private fun enviarEmailNotificacao(sol: SolicitacaoAdmin) {
        val assunto = "Atualização do Pedido - Grace App"
        val corpo = """
            Olá ${sol.nomeReceptor},
            
            Boas notícias! Sua solicitação de ${sol.itemCategoria} (${sol.itemQuantidade} un) foi marcada como entregue/retirada.
            
            Esperamos ter ajudado!
            Equipe Grace
        """.trimIndent()

        val intent = android.content.Intent(android.content.Intent.ACTION_SENDTO).apply {
            data = android.net.Uri.parse("mailto:")
            putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf(sol.emailReceptor))
            putExtra(android.content.Intent.EXTRA_SUBJECT, assunto)
            putExtra(android.content.Intent.EXTRA_TEXT, corpo)
        }

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Não foi possível abrir o app de e-mail.", Toast.LENGTH_SHORT).show()
        }
    }
}