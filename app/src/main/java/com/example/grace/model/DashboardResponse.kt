package com.example.grace.model

import com.google.gson.annotations.SerializedName

data class DashboardResponse(
    @SerializedName("total_itens") val totalItens: Int,
    @SerializedName("total_solicitacoes") val totalSolicitacoes: Int,
    @SerializedName("estoque") val estoque: List<EstoqueItem>
)