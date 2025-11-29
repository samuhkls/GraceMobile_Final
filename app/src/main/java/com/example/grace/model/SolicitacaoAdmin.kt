package com.example.grace.model

import com.google.gson.annotations.SerializedName

data class SolicitacaoAdmin(
    @SerializedName("SOLICITACAO_ID") val id: Int,
    @SerializedName("USUARIO_NOME") val nomeReceptor: String,
    @SerializedName("DOACAO_CATEGORIA") val itemCategoria: String,
    @SerializedName("DOACAO_QUANTIDADE") val itemQuantidade: Int,
    @SerializedName("DATA_SOLICITACAO") val data: String
)