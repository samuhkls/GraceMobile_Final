package com.example.grace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.adapter.EstoqueAdapter
import com.example.grace.model.DashboardResponse
import com.example.grace.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val nomeAdmin = intent.getStringExtra("USER_NAME") ?: "Administrador"
        findViewById<TextView>(R.id.tvTituloAdmin).text = "Painel de $nomeAdmin"

        // Botões
        findViewById<Button>(R.id.btnGerenciarDoacoes).setOnClickListener {
            startActivity(Intent(this, GerenciarDoacoesActivity::class.java))
        }

        findViewById<Button>(R.id.btnNovaDoacao).setOnClickListener {
            startActivity(Intent(this, NovaDoacaoActivity::class.java))
        }
        val cardSolicitacoes = findViewById<androidx.cardview.widget.CardView>(R.id.cardSolicitacoes)
        cardSolicitacoes.setOnClickListener {
            startActivity(Intent(this, SolicitacoesAdminActivity::class.java))
        }

        findViewById<Button>(R.id.btnLogoutAdmin).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        carregarDashboard()
    }

    private fun carregarDashboard() {
        val tvTotalItens = findViewById<TextView>(R.id.tvTotalItens)
        val tvSolicitacoes = findViewById<TextView>(R.id.tvSolicitacoesPendentes)
        val rvEstoque = findViewById<RecyclerView>(R.id.rvEstoqueAdmin)

        rvEstoque.layoutManager = LinearLayoutManager(this)



        RetrofitClient.apiService.getDadosAdmin().enqueue(object : Callback<DashboardResponse> {
            override fun onResponse(call: Call<DashboardResponse>, response: Response<DashboardResponse>) {
                if (response.isSuccessful) {
                    val dados = response.body()
                    if (dados != null) {

                        tvTotalItens.text = dados.totalItens.toString()
                        tvSolicitacoes.text = dados.totalSolicitacoes.toString()

                        rvEstoque.adapter = EstoqueAdapter(dados.estoque)
                    }
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                Toast.makeText(this@AdminActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}