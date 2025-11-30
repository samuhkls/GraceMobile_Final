package com.example.grace.network

import com.example.grace.model.CadastroResponse
import com.example.grace.model.DashboardResponse
import com.example.grace.model.Doacao
import com.example.grace.model.LoginResponse
import com.example.grace.model.Necessidade
import com.example.grace.model.SolicitacaoAdmin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("grace_api/login.php")
    fun login(
        @Query("usuario") usuario: String,
        @Query("senha") senha: String
    ): Call<List<LoginResponse>>


    @GET("grace_api/cadastro.php")
    fun cadastrar(
        @Query("nome") nome: String,
        @Query("email") email: String,
        @Query("cpf") cpf: String,
        @Query("senha") senha: String,
        @Query("tipo") tipo: String
    ): Call<CadastroResponse>

    @GET("grace_api/criar_doacao.php")
    fun criarDoacao(
        @Query("categoria") categoria: String,
        @Query("quantidade") quantidade: Int,
        @Query("usuario_id") usuarioId: Long
    ): Call<CadastroResponse>

    @GET("grace_api/listar_doacoes.php")
    fun listarDoacoes(): Call<List<Doacao>>

    @GET("grace_api/dados_admin.php")
    fun getDadosAdmin(): Call<DashboardResponse>

    @GET("grace_api/deletar_doacao.php")
    fun deletarDoacao(
        @Query("id") idDoacao: Int
    ): Call<CadastroResponse>

    @GET("grace_api/solicitar_doacao.php")
    fun solicitarDoacao(
        @Query("usuario_id") usuarioId: Long,
        @Query("doacao_id") doacaoId: Int
    ): Call<CadastroResponse>

    @GET("grace_api/registrar_necessidade.php")
    fun registrarNecessidade(
        @Query("usuario_id") usuarioId: Long,
        @Query("categoria") categoria: String,
        @Query("descricao") descricao: String
    ): Call<CadastroResponse>

    @GET("grace_api/listar_necessidades.php")
    fun listarNecessidades(): Call<List<Necessidade>>

    @GET("grace_api/listar_solicitacoes_admin.php")
    fun listarSolicitacoesAdmin(): Call<List<SolicitacaoAdmin>>

    @GET("grace_api/marcar_entregue.php")
    fun marcarComoEntregue(
        @Query("id") idSolicitacao: Int
    ): Call<CadastroResponse>

}