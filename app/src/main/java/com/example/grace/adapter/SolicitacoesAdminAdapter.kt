package com.example.grace.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grace.R
import com.example.grace.model.SolicitacaoAdmin

class SolicitacoesAdminAdapter(
    private val lista: List<SolicitacaoAdmin>,
    private val onEntregarClick: (SolicitacaoAdmin) -> Unit
) : RecyclerView.Adapter<SolicitacoesAdminAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome: TextView = view.findViewById(R.id.tvSolicitacaoNome)
        val item: TextView = view.findViewById(R.id.tvSolicitacaoItem)
        val data: TextView = view.findViewById(R.id.tvSolicitacaoData)
        val btnEntregue: Button = view.findViewById(R.id.btnConfirmarEntrega)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_solicitacao_admin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sol = lista[position]

        holder.nome.text = sol.nomeReceptor
        holder.item.text = "${sol.itemCategoria} (${sol.itemQuantidade} un)"
        holder.data.text = sol.data

        holder.btnEntregue.setOnClickListener {
            onEntregarClick(sol)
        }
    }

    override fun getItemCount() = lista.size
}