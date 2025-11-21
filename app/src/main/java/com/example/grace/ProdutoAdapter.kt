package com.example.grace.ui.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.R
import com.example.grace.Produto

class ProdutoAdapter(private val lista: List<Produto>) :
    RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

    class ProdutoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagem: ImageView = itemView.findViewById(R.id.ivProdutoImagem)
        val nome: TextView = itemView.findViewById(R.id.tvProdutoNome)
        val descricao: TextView = itemView.findViewById(R.id.tvProdutoDescricao)
        val quantidade: TextView = itemView.findViewById(R.id.tvProdutoQuantidade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_produto, parent, false)
        return ProdutoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val item = lista[position]
        holder.nome.text = item.nome
        holder.descricao.text = item.descricao
        holder.quantidade.text = "Quantidade: ${item.quantidade}"
        holder.imagem.setImageResource(item.imagemResId)
    }

    override fun getItemCount(): Int = lista.size
}
