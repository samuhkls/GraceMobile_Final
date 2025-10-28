package com.example.grace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.GridLayoutManager
import com.example.grace.ui.theme.GraceTheme
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.ui.theme.CategoriaAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvCategorias = findViewById<RecyclerView>(R.id.rvCategorias)

        // Lista de categorias
        val categorias = listOf(
            Categoria("Produtos de Higiene", R.drawable.ic_hygiene),
            Categoria("Alimentos Não Perecíveis", R.drawable.ic_food),
            Categoria("Roupas em Bom Estado", R.drawable.ic_clothes),
            Categoria("Livros", R.drawable.ic_books),
            Categoria("Brinquedos", R.drawable.ic_toys)
        )

        // Define o layout em grade (2 colunas)
        rvCategorias.layoutManager = GridLayoutManager(this, 2)
        rvCategorias.adapter = CategoriaAdapter(categorias)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GraceTheme {
        Greeting("Android")
    }
}