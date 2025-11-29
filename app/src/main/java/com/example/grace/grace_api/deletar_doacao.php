<?php
// deletar_doacao.php
header('Content-Type: application/json; charset=UTF-8');

// 1. Configurações (SEMPRE IGUAIS)
$host = 'www.thyagoquintas.com.br:3306';
$db   = 'engenharia_73';
$user = 'engenharia_73'; // CONFIRA SEU USUARIO
$pass = 'preguicadecoleira'; // CONFIRA SUA SENHA
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
     echo json_encode(["sucesso" => false, "mensagem" => "Erro DB"]);
     exit;
}

// 2. Receber o ID da doação a ser excluída
$id = $_GET['id'] ?? '';

if (empty($id)) {
    echo json_encode(["sucesso" => false, "mensagem" => "ID inválido."]);
    exit;
}

// 3. Deletar do Banco
$sql = "DELETE FROM DOACAO WHERE DOACAO_ID = :id";
$stmt = $pdo->prepare($sql);

try {
    $stmt->execute(['id' => $id]);
    
    // Verifica se alguma linha foi realmente afetada (se o ID existia)
    if ($stmt->rowCount() > 0) {
        echo json_encode(["sucesso" => true, "mensagem" => "Doação excluída com sucesso!"]);
    } else {
        echo json_encode(["sucesso" => false, "mensagem" => "Erro: Doação não encontrada."]);
    }
} catch (\PDOException $e) {
    echo json_encode(["sucesso" => false, "mensagem" => "Erro SQL: " . $e->getMessage()]);
}
?>