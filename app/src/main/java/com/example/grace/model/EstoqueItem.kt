package com.example.grace.model

import com.google.gson.annotations.SerializedName

// Representa uma linha do "GROUP BY": Categoria + Quantidade
data class EstoqueItem(
    @SerializedName("categoria") val categoria: String,
    @SerializedName("quantidade") val quantidade: Int
)