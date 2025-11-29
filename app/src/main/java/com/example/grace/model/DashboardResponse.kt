package com.example.grace.model

import com.google.gson.annotations.SerializedName

data class DashboardResponse(
    @SerializedName("total_itens") val totalItens: Int, // Mudou de nome (era totalDoacoes)
    @SerializedName("total_solicitacoes") val totalSolicitacoes: Int,
    @SerializedName("estoque") val estoque: List<EstoqueItem> // A lista nova!
)