<?php
header('Content-Type: application/json; charset=UTF-8');

// 1. Configurações
$host = 'www.thyagoquintas.com.br:3306';
$db   = 'engenharia_73';
$user = 'engenharia_73'; // SEU USUARIO
$pass = 'preguicadecoleira'; // SUA SENHA
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

// 2. A Super Consulta (JOIN triplo)
// Trazemos: ID do pedido, Nome do Receptor, O que é (Categoria), Quanto é, e a Data.
$sql = "SELECT 
            S.SOLICITACAO_ID,
            U.USUARIO_NOME,
            D.DOACAO_CATEGORIA,
            D.DOACAO_QUANTIDADE,
            S.DATA_SOLICITACAO
        FROM SOLICITACAO S
        JOIN USUARIO U ON S.USUARIO_ID = U.USUARIO_ID
        JOIN DOACAO D ON S.DOACAO_ID = D.DOACAO_ID
        WHERE S.STATUS = 'Pendente'
        ORDER BY S.DATA_SOLICITACAO ASC"; // Os pedidos mais antigos aparecem primeiro (fila)

try {
    $stmt = $pdo->query($sql);
    $lista = $stmt->fetchAll();
    echo json_encode($lista);
} catch (\PDOException $e) {
    echo json_encode(["erro" => $e->getMessage()]);
}
?>