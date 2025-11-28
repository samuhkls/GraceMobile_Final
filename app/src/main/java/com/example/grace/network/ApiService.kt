package com.example.grace.network

import com.example.grace.model.CadastroResponse
import com.example.grace.model.Doacao
import com.example.grace.model.LoginResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // 1. Função de LOGIN
    // Segue o estilo do professor: @GET e @Query
    // O caminho "grace_api/login.php" é o nome da pasta e arquivo no seu htdocs
    @GET("grace_api/login.php")
    fun login(
        @Query("usuario") usuario: String, // "usuario" é o nome do $_GET no PHP
        @Query("senha") senha: String      // "senha" é o nome do $_GET no PHP
    ): Call<List<LoginResponse>> // O PDF espera uma LISTA, pois o PHP devolve um array

    // 2. Função de CADASTRO
    // Segue o mesmo estilo, mas com todos os nossos campos
    @GET("grace_api/cadastro.php")
    fun cadastrar(
        @Query("nome") nome: String,     // "nome" é o nome do $_GET no cadastro.php
        @Query("email") email: String,   // "email" é o nome do $_GET no cadastro.php
        @Query("cpf") cpf: String,       // "cpf" é o nome do $_GET no cadastro.php
        @Query("senha") senha: String,   // "senha" é o nome do $_GET no cadastro.php
        @Query("tipo") tipo: String      // "tipo" é o nome do $_GET no cadastro.php
    ): Call<CadastroResponse> // Aqui esperamos um OBJETO único, não uma lista

    @GET("grace_api/criar_doacao.php")
    fun criarDoacao(
        @Query("categoria") categoria: String,
        @Query("quantidade") quantidade: Int,
        @Query("usuario_id") usuarioId: Long
    ): Call<CadastroResponse>

    @GET("grace_api/listar_doacoes.php")
    fun listarDoacoes(): Call<List<Doacao>>
}