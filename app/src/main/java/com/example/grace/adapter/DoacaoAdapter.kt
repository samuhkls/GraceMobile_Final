package com.example.grace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.R
import com.example.grace.model.Doacao

class DoacaoAdapter(private val listaDoacoes: List<Doacao>) :
    RecyclerView.Adapter<DoacaoAdapter.DoacaoViewHolder>() {

    class DoacaoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProduto: ImageView = view.findViewById(R.id.ivProdutoImagem)
        val txtNome: TextView = view.findViewById(R.id.tvProdutoNome)
        val txtDescricao: TextView = view.findViewById(R.id.tvProdutoDescricao)
        val txtQuantidade: TextView = view.findViewById(R.id.tvProdutoQuantidade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoacaoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_produto, parent, false)
        return DoacaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoacaoViewHolder, position: Int) {
        val doacao = listaDoacoes[position]

        // 1. Define os textos vindos do banco
        holder.txtNome.text = doacao.categoria
        holder.txtQuantidade.text = "Quantidade: ${doacao.quantidade}"
        holder.txtDescricao.text = "Data do registro: ${doacao.data}" // Usamos a data na descrição

        // 2. Lógica para escolher o ÍCONE correto baseado na Categoria
        val icone = when (doacao.categoria) {
            "Alimentos", "Food", "Alimentos" -> R.drawable.ic_food
            "Roupas", "Clothing", "Ropa" -> R.drawable.ic_clothes
            "Higiene", "Hygiene" -> R.drawable.ic_hygiene
            "Brinquedos", "Toys", "Juguetes" -> R.drawable.ic_toys
            "Livros", "Books", "Libros" -> R.drawable.ic_books
            else -> R.drawable.ic_launcher_foreground // Ícone padrão se não achar
        }
        holder.imgProduto.setImageResource(icone)
    }

    override fun getItemCount() = listaDoacoes.size
}