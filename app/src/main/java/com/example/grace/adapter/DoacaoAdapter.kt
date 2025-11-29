package com.example.grace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.R
import com.example.grace.model.Doacao

// Recebe a função 'onSolicitarClick'
class DoacaoAdapter(
    private val listaDoacoes: List<Doacao>,
    private val onSolicitarClick: (Doacao) -> Unit
) : RecyclerView.Adapter<DoacaoAdapter.DoacaoViewHolder>() {

    class DoacaoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProduto: ImageView = view.findViewById(R.id.ivProdutoImagem)
        val txtNome: TextView = view.findViewById(R.id.tvProdutoNome)
        val txtDescricao: TextView = view.findViewById(R.id.tvProdutoDescricao)
        val txtQuantidade: TextView = view.findViewById(R.id.tvProdutoQuantidade)
        val btnSolicitar: Button = view.findViewById(R.id.btnSolicitar) // O botão novo!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoacaoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_produto, parent, false)
        return DoacaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoacaoViewHolder, position: Int) {
        val doacao = listaDoacoes[position]

        holder.txtNome.text = doacao.categoria
        holder.txtQuantidade.text = "Qtd: ${doacao.quantidade}"
        holder.txtDescricao.text = "Data: ${doacao.data}"

        val icone = when (doacao.categoria) { // Nota: em alguns adapters pode ser 'doacao.categoria' ou 'sol.itemCategoria'
            "Alimentos", "Food" -> R.drawable.ic_food
            "Roupas", "Clothing", "Ropa", "Roupas em Bom Estado" -> R.drawable.ic_clothes
            "Higiene", "Hygiene", "Produtos de Higiene", "Hygiene Products", "Productos de Higiene" -> R.drawable.ic_hygiene
            "Brinquedos", "Toys", "Juguetes" -> R.drawable.ic_toys
            "Livros", "Books", "Libros" -> R.drawable.ic_books
            else -> R.drawable.ic_launcher_foreground // Ícone padrão
        }
        holder.imgProduto.setImageResource(icone)

        // Clique do botão PEDIR
        holder.btnSolicitar.setOnClickListener {
            onSolicitarClick(doacao)
        }
    }

    override fun getItemCount() = listaDoacoes.size
}