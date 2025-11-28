package com.example.grace.model

import com.google.gson.annotations.SerializedName

data class Doacao(
    @SerializedName("DOACAO_ID") val id: Int,
    @SerializedName("DOACAO_CATEGORIA") val categoria: String,
    @SerializedName("DOACAO_QUANTIDADE") val quantidade: Int,
    @SerializedName("DATA_DOACAO") val data: String
)