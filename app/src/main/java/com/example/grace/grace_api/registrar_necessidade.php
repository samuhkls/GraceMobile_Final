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
     echo json_encode(["sucesso" => false, "mensagem" => "Erro DB"]);
     exit;
}

// 2. Receber dados
$usuario_id = $_GET['usuario_id'] ?? '';
$categoria = $_GET['categoria'] ?? '';
$descricao = $_GET['descricao'] ?? '';

if (empty($usuario_id) || empty($categoria) || empty($descricao)) {
    echo json_encode(["sucesso" => false, "mensagem" => "Preencha todos os campos."]);
    exit;
}

// 3. Inserir
$sql = "INSERT INTO NECESSIDADE (USUARIO_ID, NECESSIDADE_CATEGORIA, NECESSIDADE_DESCRICAO) VALUES (:uid, :cat, :desc)";
$stmt = $pdo->prepare($sql);

try {
    $stmt->execute(['uid' => $usuario_id, 'cat' => $categoria, 'desc' => $descricao]);
    echo json_encode(["sucesso" => true, "mensagem" => "Pedido de necessidade registrado!"]);
} catch (\PDOException $e) {
    echo json_encode(["sucesso" => false, "mensagem" => "Erro: " . $e->getMessage()]);
}
?>