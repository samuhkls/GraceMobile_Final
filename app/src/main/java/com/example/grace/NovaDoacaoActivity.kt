package com.example.grace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import com.example.grace.model.CadastroResponse
import com.example.grace.network.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovaDoacaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_doacao)
        val userId = intent.getLongExtra("USER_ID", -1)

        if (userId == -1L) {
            Toast.makeText(this, "Erro: Usuário não identificado. Faça login novamente.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val categorias = listOf(
            getString(R.string.categoria_alimentos),
            getString(R.string.categoria_roupas),
            getString(R.string.categoria_higiene),
            getString(R.string.categoria_brinquedos),
            getString(R.string.categoria_livros)
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categorias)
        val actvCategoria = findViewById<AutoCompleteTextView>(R.id.actvCategoria)
        actvCategoria.setAdapter(adapter)

        val etQuantidade = findViewById<TextInputEditText>(R.id.etQuantidadeDoacao)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrarDoacao)

        btnRegistrar.setOnClickListener {
            val categoriaSelecionada = actvCategoria.text.toString()
            val quantidadeTexto = etQuantidade.text.toString()
            if (categoriaSelecionada.isEmpty() || quantidadeTexto.isEmpty()) {
                Toast.makeText(this, getString(R.string.erro_campo_obrigatorio), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val quantidade = quantidadeTexto.toIntOrNull()
            if (quantidade == null || quantidade <= 0) {
                Toast.makeText(this, "Quantidade inválida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val call = RetrofitClient.apiService.criarDoacao(
                categoria = categoriaSelecionada,
                quantidade = quantidade,
                usuarioId = userId
            )

            call.enqueue(object : Callback<CadastroResponse> {
                override fun onResponse(call: Call<CadastroResponse>, response: Response<CadastroResponse>) {
                    if (response.isSuccessful) {
                        val resposta = response.body()
                        if (resposta?.sucesso == true) {

                            Toast.makeText(this@NovaDoacaoActivity, resposta.mensagem, Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(this@NovaDoacaoActivity, resposta?.mensagem ?: "Erro desconhecido", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@NovaDoacaoActivity, "Erro no servidor", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CadastroResponse>, t: Throwable) {
                    Toast.makeText(this@NovaDoacaoActivity, "Erro de conexão: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}