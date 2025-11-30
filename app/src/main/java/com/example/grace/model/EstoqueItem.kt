package com.example.grace.model

import com.google.gson.annotations.SerializedName


data class EstoqueItem(
    @SerializedName("categoria") val categoria: String,
    @SerializedName("quantidade") val quantidade: Int
)