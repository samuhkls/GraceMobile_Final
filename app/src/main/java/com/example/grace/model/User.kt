package com.example.grace.model

import com.google.gson.annotations.SerializedName

data class User(
    // @SerializedName diz ao Gson: "No JSON, o nome é 'id',
    // mas no meu código Kotlin, eu quero chamar de 'id'."
    @SerializedName("id") val id: Long,

    @SerializedName("nome") val nome: String,

    @SerializedName("email") val email: String,

    // Ex: "No JSON, o nome é 'tipo_usuario', mas no Kotlin é 'tipoUsuario'."
    @SerializedName("tipo_usuario") val tipoUsuario: String
)
