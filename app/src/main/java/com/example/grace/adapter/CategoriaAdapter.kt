package com.example.grace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.R
import com.example.grace.model.Categoria

// Agora recebemos uma função 'onClick' no construtor
class CategoriaAdapter(
    private val categorias: List<Categoria>,
    private val onClick: (Categoria) -> Unit
) : RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    class CategoriaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategoria: TextView = view.findViewById(R.id.tvCategoria)
        val ivCategoria: ImageView = view.findViewById(R.id.ivCategoria)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, posicao: Int) {
        val categoria = categorias[posicao]
        holder.tvCategoria.text = categoria.nome
        holder.ivCategoria.setImageResource(categoria.iconeResId)

        // Quando clicar, chama a função da Activity
        holder.itemView.setOnClickListener {
            onClick(categoria)
        }
    }

    override fun getItemCount() = categorias.size
}