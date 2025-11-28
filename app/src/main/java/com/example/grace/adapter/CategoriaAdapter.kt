package com.example.grace.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.ConsultaActivity
import com.example.grace.R
import com.example.grace.model.Categoria

class CategoriaAdapter(private val categorias: List<Categoria>) :
    RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {
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
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ConsultaActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = categorias.size
}