<?php
// Configurações do Banco de Dados
$servidor = "localhost"; 
$usuario = "root";       
$senha_db = "";          
$banco = "engenharia_73";

// Tenta criar a conexão
$conexao = new mysqli($servidor, $usuario, $senha_db, $banco);

// Verifica se a conexão falhou
if ($conexao->connect_error) {
    die("Conexão falhou: " . $conexao->connect_error);
}

$conexao->set_charset("utf8");

?>