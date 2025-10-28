package com.example.grace.ui.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.R
import com.example.grace.Categoria

class CategoriaAdapter(private val lista: List<Categoria>) :
    RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icone: ImageView = itemView.findViewById(R.id.ivCategoria)
        val nome: TextView = itemView.findViewById(R.id.tvCategoria)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val item = lista[position]
        holder.nome.text = item.nome
        holder.icone.setImageResource(item.icone)
    }

    override fun getItemCount(): Int = lista.size
}
