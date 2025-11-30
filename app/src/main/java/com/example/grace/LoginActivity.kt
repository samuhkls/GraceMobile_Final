package com.example.grace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.grace.model.LoginResponse
import com.example.grace.network.RetrofitClient // <-- IMPORTA NOSSO CLIENTE
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call // <-- IMPORTA O RETROFIT
import retrofit2.Callback // <-- IMPORTA O RETROFIT
import retrofit2.Response // <-- IMPORTA O RETROFIT

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etSenha = findViewById<TextInputEditText>(R.id.etSenha)
        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val tilSenha = findViewById<TextInputLayout>(R.id.tilSenha)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val tvCadastrar = findViewById<TextView>(R.id.tvCadastrar)

        tvCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        btnEntrar.setOnClickListener {
            val email = etEmail.text.toString()
            val senha = etSenha.text.toString()

            tilEmail.error = null
            tilSenha.error = null

            if (email.isEmpty()) {
                tilEmail.error = getString(R.string.erro_campo_obrigatorio)
                return@setOnClickListener // Para a execução aqui
            }
            if (senha.isEmpty()) {
                tilSenha.error = getString(R.string.erro_campo_obrigatorio)
                return@setOnClickListener
            }

            val apiService = RetrofitClient.apiService

            val call = apiService.login(usuario = email, senha = senha)

            call.enqueue(object : Callback<List<LoginResponse>> {

                override fun onResponse(
                    call: Call<List<LoginResponse>>,
                    response: Response<List<LoginResponse>>
                ) {
                    if (response.isSuccessful) {
                        val loginResponses = response.body()
                        if (loginResponses != null && loginResponses.isNotEmpty()) {
                            // 1. Pegamos o usuário que veio do banco
                            val usuarioLogado = loginResponses[0]

                            Toast.makeText(this@LoginActivity, "Bem-vindo, ${usuarioLogado.usuarioNome}!", Toast.LENGTH_SHORT).show()

                            val intent: Intent
                            if (usuarioLogado.usuarioTipo.equals("Admin", ignoreCase = true)) {
                                intent = Intent(this@LoginActivity, AdminActivity::class.java)
                            } else {
                                intent = Intent(this@LoginActivity, MainActivity::class.java)
                            }
                            // passando os dados para a próxima tela (qualquer uma)
                            intent.putExtra("USER_NAME", usuarioLogado.usuarioNome)
                            intent.putExtra("USER_ID", usuarioLogado.usuariold)
                            intent.putExtra("USER_TYPE", usuarioLogado.usuarioTipo)

                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)

                        } else {
                            Toast.makeText(this@LoginActivity, "Usuário ou senha inválidos", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Erro no login", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<LoginResponse>>, t: Throwable) {

                    Toast.makeText(this@LoginActivity, "Erro de conexão: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
        val tvEsqueceu = findViewById<TextView>(R.id.tvEsqueceu)

        tvEsqueceu.setOnClickListener {
            val emailSuporte = "suporte.grace.app@gmail.com"
            val assunto = "Recuperação de Senha - Grace App"
            val corpo = "Olá equipe Grace,\n\nEsqueci minha senha e gostaria de solicitar a redefinição.\n\nMeu usuário/email cadastrado é: "

            // 3. Cria a Intent de E-mail
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = android.net.Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(emailSuporte))
                putExtra(Intent.EXTRA_SUBJECT, assunto)
                putExtra(Intent.EXTRA_TEXT, corpo)
            }

            try {
                startActivity(intent)
            } catch (e: Exception) {

                Toast.makeText(this, "Nenhum aplicativo de e-mail encontrado.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}