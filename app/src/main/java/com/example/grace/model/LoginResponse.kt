package com.example.grace.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("usuariold") val usuariold: Long,
    @SerializedName("usuarioNome") val usuarioNome: String,
    @SerializedName("usuarioEmail") val usuarioEmail: String,
    @SerializedName("usuarioCpf") val usuarioCpf: String,
    @SerializedName("usuarioTipo") val usuarioTipo: String
)
