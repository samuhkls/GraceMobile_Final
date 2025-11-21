package com.example.grace.model

import com.google.gson.annotations.SerializedName

data class CadastroResponse(
    @SerializedName("sucesso") val sucesso: Boolean,
    @SerializedName("mensagem") val mensagem: String
)
