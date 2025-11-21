package com.example.grace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.ui.theme.ProdutoAdapter

class ConsultaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta)

        val rvProdutos = findViewById<RecyclerView>(R.id.rvProdutos)

        val produtos = listOf(
            Produto("Arroz 5kg", "Pacote de arroz integral doado por membros.", 10, R.drawable.ic_food),
            Produto("Sabonetes", "Sabonetes em barra variados.", 25, R.drawable.ic_hygiene),
            Produto("Camisas", "Roupas em bom estado para adultos e crianças.", 15, R.drawable.ic_clothes),
            Produto("Livros", "Coleção de livros infantis e educacionais.", 8, R.drawable.ic_books),
            Produto("Brinquedos", "Brinquedos diversos em ótimo estado.", 12, R.drawable.ic_toys)
        )

        rvProdutos.layoutManager = LinearLayoutManager(this)
        rvProdutos.adapter = ProdutoAdapter(produtos)
    }
}
