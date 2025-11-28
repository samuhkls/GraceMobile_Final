package com.example.grace

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.adapter.DoacaoAdapter // <-- Use o novo adapter
import com.example.grace.model.Doacao
import com.example.grace.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsultaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta)

        val rvProdutos = findViewById<RecyclerView>(R.id.rvProdutos)
        rvProdutos.layoutManager = LinearLayoutManager(this)

        // --- CHAMADA DA API ---
        val call = RetrofitClient.apiService.listarDoacoes()

        call.enqueue(object : Callback<List<Doacao>> {
            override fun onResponse(call: Call<List<Doacao>>, response: Response<List<Doacao>>) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()

                    // Configura o Adapter com a lista que veio do PHP
                    rvProdutos.adapter = DoacaoAdapter(lista)

                    if (lista.isEmpty()) {
                        Toast.makeText(this@ConsultaActivity, "Nenhuma doação encontrada.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ConsultaActivity, "Erro no servidor", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Doacao>>, t: Throwable) {
                Toast.makeText(this@ConsultaActivity, "Erro de conexão: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}