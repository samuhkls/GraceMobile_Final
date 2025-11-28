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

        // 1. Recebe o ID do Usuário (passado pela MainActivity)
        val userId = intent.getLongExtra("USER_ID", -1)

        // Verifica se o ID é válido (segurança básica)
        if (userId == -1L) {
            Toast.makeText(this, "Erro: Usuário não identificado. Faça login novamente.", Toast.LENGTH_LONG).show()
            finish() // Fecha a tela se não tiver usuário
            return
        }

        // 2. Configura o Dropdown (Categorias)
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

        // 3. Configura o Botão de Registrar
        val etQuantidade = findViewById<TextInputEditText>(R.id.etQuantidadeDoacao)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrarDoacao)

        btnRegistrar.setOnClickListener {
            // a. Coleta os dados
            val categoriaSelecionada = actvCategoria.text.toString()
            val quantidadeTexto = etQuantidade.text.toString()

            // b. Validação simples
            if (categoriaSelecionada.isEmpty() || quantidadeTexto.isEmpty()) {
                Toast.makeText(this, getString(R.string.erro_campo_obrigatorio), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Converte quantidade para Inteiro
            val quantidade = quantidadeTexto.toIntOrNull()
            if (quantidade == null || quantidade <= 0) {
                Toast.makeText(this, "Quantidade inválida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // c. CHAMA A API (Retrofit)
            val call = RetrofitClient.apiService.criarDoacao(
                categoria = categoriaSelecionada,
                quantidade = quantidade,
                usuarioId = userId // O ID que veio lá do Login!
            )

            call.enqueue(object : Callback<CadastroResponse> {
                override fun onResponse(call: Call<CadastroResponse>, response: Response<CadastroResponse>) {
                    if (response.isSuccessful) {
                        val resposta = response.body()
                        if (resposta?.sucesso == true) {
                            // SUCESSO!
                            Toast.makeText(this@NovaDoacaoActivity, resposta.mensagem, Toast.LENGTH_LONG).show()
                            finish() // Fecha a tela e volta pra Main
                        } else {
                            // Erro do PHP
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