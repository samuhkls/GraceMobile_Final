<?php
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

// 2. Receber IDs via GET
$usuario_id = $_GET['usuario_id'] ?? '';
$doacao_id = $_GET['doacao_id'] ?? '';

if (empty($usuario_id) || empty($doacao_id)) {
    echo json_encode(["sucesso" => false, "mensagem" => "Dados inválidos."]);
    exit;
}

// 3. Verificar se já pediu (Regra de Negócio: Não pode pedir a mesma coisa 2x)
$check = $pdo->prepare("SELECT * FROM SOLICITACAO WHERE USUARIO_ID = ? AND DOACAO_ID = ?");
$check->execute([$usuario_id, $doacao_id]);

if ($check->rowCount() > 0) {
    echo json_encode(["sucesso" => false, "mensagem" => "Você já solicitou este item!"]);
    exit;
}

// 4. Inserir Solicitação
$sql = "INSERT INTO SOLICITACAO (USUARIO_ID, DOACAO_ID) VALUES (:uid, :did)";
$stmt = $pdo->prepare($sql);

try {
    $stmt->execute(['uid' => $usuario_id, 'did' => $doacao_id]);
    echo json_encode(["sucesso" => true, "mensagem" => "Solicitação enviada com sucesso!"]);
} catch (\PDOException $e) {
    echo json_encode(["sucesso" => false, "mensagem" => "Erro ao solicitar: " . $e->getMessage()]);
}
?>