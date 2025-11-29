package com.example.grace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.R
import com.example.grace.model.EstoqueItem

class EstoqueAdapter(private val listaEstoque: List<EstoqueItem>) :
    RecyclerView.Adapter<EstoqueAdapter.EstoqueViewHolder>() {

    class EstoqueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icone: ImageView = view.findViewById(R.id.ivEstoqueIcone)
        val categoria: TextView = view.findViewById(R.id.tvEstoqueCategoria)
        val quantidade: TextView = view.findViewById(R.id.tvEstoqueQuantidade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstoqueViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_estoque, parent, false)
        return EstoqueViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstoqueViewHolder, position: Int) {
        val item = listaEstoque[position]

        holder.categoria.text = item.categoria
        holder.quantidade.text = "${item.quantidade} un"

        // Lógica visual para escolher o ícone
        val iconeRes = when (item.categoria) { // Nota: em alguns adapters pode ser 'doacao.categoria' ou 'sol.itemCategoria'
            "Alimentos", "Food" -> R.drawable.ic_food
            "Roupas", "Clothing", "Ropa", "Roupas em Bom Estado" -> R.drawable.ic_clothes
            "Higiene", "Hygiene", "Produtos de Higiene", "Hygiene Products", "Productos de Higiene" -> R.drawable.ic_hygiene
            "Brinquedos", "Toys", "Juguetes" -> R.drawable.ic_toys
            "Livros", "Books", "Libros" -> R.drawable.ic_books
            else -> R.drawable.ic_launcher_foreground // Ícone padrão
        }
        holder.icone.setImageResource(iconeRes)
    }

    override fun getItemCount() = listaEstoque.size
}