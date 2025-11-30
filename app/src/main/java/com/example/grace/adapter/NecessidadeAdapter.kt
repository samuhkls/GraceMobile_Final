package com.example.grace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.R
import com.example.grace.model.Necessidade

class NecessidadeAdapter(private val lista: List<Necessidade>) :
    RecyclerView.Adapter<NecessidadeAdapter.NecessidadeViewHolder>() {

    class NecessidadeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icone: ImageView = view.findViewById(R.id.ivNecessidadeIcone)
        val categoria: TextView = view.findViewById(R.id.tvNecessidadeCategoria)
        val descricao: TextView = view.findViewById(R.id.tvNecessidadeDescricao)
        val info: TextView = view.findViewById(R.id.tvNecessidadeInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NecessidadeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_necessidade, parent, false)
        return NecessidadeViewHolder(view)
    }

    override fun onBindViewHolder(holder: NecessidadeViewHolder, position: Int) {
        val item = lista[position]

        holder.categoria.text = item.categoria
        holder.descricao.text = item.descricao
        holder.info.text = "Pedido por: ${item.nomeSolicitante} em ${item.data}"


        val iconeRes = when (item.categoria) {
            "Alimentos", "Food" -> R.drawable.ic_food
            "Roupas", "Clothing", "Ropa", "Roupas em Bom Estado" -> R.drawable.ic_clothes
            "Higiene", "Hygiene", "Produtos de Higiene", "Hygiene Products", "Productos de Higiene" -> R.drawable.ic_hygiene
            "Brinquedos", "Toys", "Juguetes" -> R.drawable.ic_toys
            "Livros", "Books", "Libros" -> R.drawable.ic_books
            else -> R.drawable.ic_launcher_foreground
        }
        holder.icone.setImageResource(iconeRes)
    }
    override fun getItemCount() = lista.size
}