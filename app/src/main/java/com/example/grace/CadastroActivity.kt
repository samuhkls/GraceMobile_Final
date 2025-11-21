package com.example.grace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import com.example.grace.model.CadastroResponse
import com.example.grace.network.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)


        val etNome = findViewById<TextInputEditText>(R.id.etNome)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etCpf = findViewById<TextInputEditText>(R.id.etCpf)
        val etSenha = findViewById<TextInputEditText>(R.id.etSenha)
        val etConfirmarSenha = findViewById<TextInputEditText>(R.id.etConfirmarSenha)
        val rgTipoUsuario = findViewById<RadioGroup>(R.id.rgTipoUsuario)

        val tilNome = findViewById<TextInputLayout>(R.id.tilNome)
        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmailCadastro)
        val tilCpf = findViewById<TextInputLayout>(R.id.tilCpf)
        val tilSenha = findViewById<TextInputLayout>(R.id.tilSenhaCadastro)
        val tilConfirmarSenha = findViewById<TextInputLayout>(R.id.tilConfirmarSenha)

        val btnCadastrar = findViewById<Button>(R.id.btnCadastrar)


        btnCadastrar.setOnClickListener {

            val nome = etNome.text.toString()
            val email = etEmail.text.toString()
            val cpf = etCpf.text.toString()
            val senha = etSenha.text.toString()
            val confirmarSenha = etConfirmarSenha.text.toString()

            val tipoUsuarioIdSelecionado = rgTipoUsuario.checkedRadioButtonId
            val rbTipoUsuario = findViewById<android.widget.RadioButton>(tipoUsuarioIdSelecionado)
            val tipoUsuario = rbTipoUsuario.text.toString()

            tilNome.error = null
            tilEmail.error = null
            tilCpf.error = null
            tilSenha.error = null
            tilConfirmarSenha.error = null

            var camposValidos = true
            if (nome.isEmpty()) {
                tilNome.error = getString(R.string.erro_campo_obrigatorio)
                camposValidos = false
            }
            if (email.isEmpty()) {
                tilEmail.error = getString(R.string.erro_campo_obrigatorio)
                camposValidos = false
            }
            if (cpf.isEmpty()) {
                tilCpf.error = getString(R.string.erro_campo_obrigatorio)
                camposValidos = false
            }
            if (senha.isEmpty()) {
                tilSenha.error = getString(R.string.erro_campo_obrigatorio)
                camposValidos = false
            }
            if (confirmarSenha.isEmpty()) {
                tilConfirmarSenha.error = getString(R.string.erro_campo_obrigatorio)
                camposValidos = false
            }
            if (senha.isNotEmpty() && senha != confirmarSenha) {
                tilConfirmarSenha.error = getString(R.string.erro_senhas_nao_conferem)
                camposValidos = false
            }


            if (camposValidos) {

                val apiService = RetrofitClient.apiService
                val call = apiService.cadastrar(
                    nome = nome,
                    email = email,
                    cpf = cpf,
                    senha = senha,
                    tipo = tipoUsuario
                )
                call.enqueue(object : Callback<CadastroResponse> {

                    override fun onResponse(
                        call: Call<CadastroResponse>,
                        response: Response<CadastroResponse>
                    ) {
                        if (response.isSuccessful) {
                            val cadastroResponse = response.body()

                            if (cadastroResponse?.sucesso == true) {
                                Toast.makeText(
                                    this@CadastroActivity,
                                    cadastroResponse.mensagem,
                                    Toast.LENGTH_LONG
                                ).show()

                                finish()

                            } else {
                                Toast.makeText(
                                    this@CadastroActivity,
                                    cadastroResponse?.mensagem ?: "Erro ao cadastrar",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            Toast.makeText(this@CadastroActivity, "Erro no servidor", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<CadastroResponse>, t: Throwable) {
                        Toast.makeText(this@CadastroActivity, "Erro de conexão: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}