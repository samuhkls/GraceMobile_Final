<?php
header('Content-Type: application/json; charset=UTF-8');

// 1. Configurações do Banco
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
     echo json_encode(["sucesso" => false, "mensagem" => "Erro de conexão DB"]);
     exit;
}

// 2. Receber ID
$id = $_GET['id'] ?? '';

if (empty($id)) {
    echo json_encode(["sucesso" => false, "mensagem" => "ID inválido."]);
    exit;
}

// 3. Atualizar Status para 'Entregue'
$sql = "UPDATE SOLICITACAO SET STATUS = 'Entregue' WHERE SOLICITACAO_ID = :id";
$stmt = $pdo->prepare($sql);

try {
    $stmt->execute(['id' => $id]);
    
    if ($stmt->rowCount() > 0) {
        echo json_encode(["sucesso" => true, "mensagem" => "Entrega confirmada!"]);
    } else {
        // Se não afetou nenhuma linha (ID não existe ou já estava entregue)
        echo json_encode(["sucesso" => false, "mensagem" => "Solicitação não encontrada ou já processada."]);
    }
} catch (\PDOException $e) {
    echo json_encode(["sucesso" => false, "mensagem" => "Erro SQL: " . $e->getMessage()]);
}
?>