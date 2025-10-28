package com.example.grace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.grace.ui.theme.GraceTheme
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Diz qual layout XML esta classe deve usar
        setContentView(R.layout.activity_login) // O nome do seu XML de login

        // 2. Encontra o TextView "Cadastre-se" pelo ID que você deu no XML
        val tvCadastrar = findViewById<TextView>(R.id.tvCadastrar)

        // 3. Adiciona um "ouvinte" de clique
        tvCadastrar.setOnClickListener {
            // 4. Cria a "Intenção" de abrir a CadastroActivity
            val intent = Intent(this, CadastroActivity::class.java)

            // 5. Executa a intenção (abre a tela)
            startActivity(intent)
        }
    }
}

@Composable
fun Greeting5(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    GraceTheme {
        Greeting5("Android")
    }
}