package com.example.grace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.R
import com.example.grace.model.Doacao


class GerenciarAdapter(
    private val listaDoacoes: List<Doacao>,
    private val onDeleteClick: (Doacao) -> Unit
) : RecyclerView.Adapter<GerenciarAdapter.GerenciarViewHolder>() {

    class GerenciarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.ivGerenciarImagem)
        val nome: TextView = view.findViewById(R.id.tvGerenciarNome)
        val qtd: TextView = view.findViewById(R.id.tvGerenciarQtd)
        val data: TextView = view.findViewById(R.id.tvGerenciarData)
        val btnExcluir: ImageButton = view.findViewById(R.id.btnExcluir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GerenciarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gerenciar_doacao, parent, false)
        return GerenciarViewHolder(view)
    }

    override fun onBindViewHolder(holder: GerenciarViewHolder, position: Int) {
        val doacao = listaDoacoes[position]

        holder.nome.text = doacao.categoria
        holder.qtd.text = "Qtd: ${doacao.quantidade}"
        holder.data.text = doacao.data

        val icone = when (doacao.categoria) {
            "Alimentos", "Food" -> R.drawable.ic_food
            "Roupas", "Clothing", "Ropa", "Roupas em Bom Estado" -> R.drawable.ic_clothes
            "Higiene", "Hygiene", "Produtos de Higiene", "Hygiene Products", "Productos de Higiene" -> R.drawable.ic_hygiene
            "Brinquedos", "Toys", "Juguetes" -> R.drawable.ic_toys
            "Livros", "Books", "Libros" -> R.drawable.ic_books
            else -> R.drawable.ic_launcher_foreground
        }
        holder.img.setImageResource(icone)


        holder.btnExcluir.setOnClickListener {

            onDeleteClick(doacao)
        }
    }

    override fun getItemCount() = listaDoacoes.size
}