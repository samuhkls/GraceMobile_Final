package com.example.grace

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.adapter.DoacaoAdapter
import com.example.grace.model.CadastroResponse
import com.example.grace.model.Doacao
import com.example.grace.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ConsultaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta)

        // 1. Pega o ID do usuário que veio da Main
        val userId = intent.getLongExtra("USER_ID", -1)

        val rvProdutos = findViewById<RecyclerView>(R.id.rvProdutos)
        rvProdutos.layoutManager = LinearLayoutManager(this)

        val call = RetrofitClient.apiService.listarDoacoes()

        call.enqueue(object : Callback<List<Doacao>> {
            override fun onResponse(call: Call<List<Doacao>>, response: Response<List<Doacao>>) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()

                    // 2. Configura o adapter com a lógica de clique
                    rvProdutos.adapter = DoacaoAdapter(lista) { doacaoSelecionada ->
                        // Quando clicar em Pedir:
                        fazerSolicitacao(userId, doacaoSelecionada.id)
                    }

                    if (lista.isEmpty()) {
                        Toast.makeText(this@ConsultaActivity, "Nenhuma doação disponível.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<Doacao>>, t: Throwable) {
                Toast.makeText(this@ConsultaActivity, "Erro de conexão", Toast.LENGTH_SHORT).show()
            }
        })

        val fabSolicitar = findViewById<ExtendedFloatingActionButton>(R.id.fabSolicitarAlgo)


        fabSolicitar.setOnClickListener {
            mostrarPopupNecessidade(userId)
        }
    }

    private fun fazerSolicitacao(userId: Long, doacaoId: Int) {
        if (userId == -1L) {
            Toast.makeText(this, "Erro: Usuário não identificado.", Toast.LENGTH_SHORT).show()
            return
        }

        // Confirmação visual (Opcional, mas elegante)
        AlertDialog.Builder(this)
            .setTitle("Confirmar Solicitação")
            .setMessage("Deseja solicitar este item?")
            .setPositiveButton("Sim") { _, _ ->
                enviarPedidoParaAPI(userId, doacaoId)
            }
            .setNegativeButton("Não", null)
            .show()
    }

    private fun enviarPedidoParaAPI(userId: Long, doacaoId: Int) {
        val call = RetrofitClient.apiService.solicitarDoacao(userId, doacaoId)

        call.enqueue(object : Callback<CadastroResponse> {
            override fun onResponse(call: Call<CadastroResponse>, response: Response<CadastroResponse>) {
                if (response.isSuccessful) {
                    val resp = response.body()
                    if (resp?.sucesso == true) {
                        Toast.makeText(this@ConsultaActivity, "Pedido realizado com sucesso!", Toast.LENGTH_LONG).show()
                        // Opcional: Voltar para a tela principal
                        finish()
                    } else {
                        Toast.makeText(this@ConsultaActivity, resp?.mensagem ?: "Erro", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<CadastroResponse>, t: Throwable) {
                Toast.makeText(this@ConsultaActivity, "Erro de conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarPopupNecessidade(userId: Long) {
        if (userId == -1L) return

        // 1. Criar o layout do popup via código (ou inflar um XML separado)
        // Para ser rápido, vamos criar um layout programaticamente dentro do Dialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Solicitar Item Indisponível")
        builder.setMessage("Diga o que você precisa e nós tentaremos conseguir!")

        // Cria um container para os inputs
        val layout = android.widget.LinearLayout(this)
        layout.orientation = android.widget.LinearLayout.VERTICAL
        layout.setPadding(50, 20, 50, 20)

        // Input 1: Categoria (Spinner)
        val spinnerCategoria = Spinner(this)
        val categorias = listOf("Alimentos", "Roupas", "Higiene", "Brinquedos", "Livros")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categorias)
        spinnerCategoria.adapter = adapter
        layout.addView(spinnerCategoria)

        // Input 2: Descrição (EditText)
        val inputDescricao = EditText(this)
        inputDescricao.hint = "Descreva: Ex: Leite sem lactose, Casaco G..."
        layout.addView(inputDescricao)

        builder.setView(layout)

        // Botões do Popup
        builder.setPositiveButton("Enviar Pedido") { _, _ ->
            val categoria = spinnerCategoria.selectedItem.toString()
            val descricao = inputDescricao.text.toString()

            if (descricao.isNotEmpty()) {
                enviarNecessidadeAPI(userId, categoria, descricao)
            } else {
                Toast.makeText(this, "Escreva uma descrição", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar", null)

        builder.show()
    }

    private fun enviarNecessidadeAPI(userId: Long, categoria: String, descricao: String) {
        val call = RetrofitClient.apiService.registrarNecessidade(userId, categoria, descricao)

        call.enqueue(object : Callback<CadastroResponse> {
            override fun onResponse(call: Call<CadastroResponse>, response: Response<CadastroResponse>) {
                if (response.isSuccessful && response.body()?.sucesso == true) {
                    Toast.makeText(this@ConsultaActivity, "Pedido registrado! Avisaremos se chegar.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@ConsultaActivity, "Erro ao registrar.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<CadastroResponse>, t: Throwable) {
                Toast.makeText(this@ConsultaActivity, "Erro de conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }
}