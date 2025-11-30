package com.example.grace

import android.Manifest // Importante para a permissão
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager // Importante
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }
        }

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
                                enviarNotificacaoSucesso()

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

    private fun enviarNotificacaoSucesso() {
        val channelId = "canal_cadastro_grace"
        val notificationId = 1

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Cadastro Grace"
            val descriptionText = "Notificações de cadastro"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo_grace)
            .setContentTitle("Bem-vindo(a) ao Grace!")
            .setContentText("Seu cadastro foi realizado com sucesso. Faça login para começar.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        try {
            notificationManager.notify(notificationId, builder.build())
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}