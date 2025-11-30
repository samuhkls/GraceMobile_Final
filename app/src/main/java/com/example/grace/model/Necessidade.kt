package com.example.grace.model

import com.google.gson.annotations.SerializedName

data class Necessidade(
    @SerializedName("NECESSIDADE_ID") val id: Int,
    @SerializedName("NECESSIDADE_CATEGORIA") val categoria: String,
    @SerializedName("NECESSIDADE_DESCRICAO") val descricao: String,
    @SerializedName("USUARIO_NOME") val nomeSolicitante: String,
    @SerializedName("DATA_PEDIDO") val data: String
)