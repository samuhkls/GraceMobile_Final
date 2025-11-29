<?php
header('Content-Type: application/json; charset=UTF-8');

// 1. Configurações
$host = 'www.thyagoquintas.com.br:3306';
$db   = 'engenharia_73';
$user = 'engenharia_73';
$pass = 'preguicadecoleira';
$charset = 'utf8mb4';

$dsn = "mysql:host=$host;dbname=$db;charset=$charset";
$options = [
    PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    PDO::ATTR_EMULATE_PREPARES => false,
];

try {
     $pdo = new PDO($dsn, $user, $pass, $options);
} catch (\PDOException $e) {
     echo json_encode([]);
     exit;
}

// 2. Consulta: Pegar necessidades pendentes + Nome de quem pediu (JOIN)
// O JOIN é legal aqui para o Doador saber: "Ah, foi o João que pediu".
$sql = "SELECT 
            N.NECESSIDADE_ID, 
            N.NECESSIDADE_CATEGORIA, 
            N.NECESSIDADE_DESCRICAO, 
            N.DATA_PEDIDO,
            U.USUARIO_NOME
        FROM NECESSIDADE N
        JOIN USUARIO U ON N.USUARIO_ID = U.USUARIO_ID
        WHERE N.STATUS = 'Pendente'
        ORDER BY N.DATA_PEDIDO DESC";

try {
    $stmt = $pdo->query($sql);
    $pedidos = $stmt->fetchAll();
    echo json_encode($pedidos);
} catch (\PDOException $e) {
    echo json_encode(["erro" => $e->getMessage()]);
}
?>