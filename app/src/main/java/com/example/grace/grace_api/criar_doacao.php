<?php
// Configurações do Banco (IGUAL AO SEU LOGIN.PHP)
$host = 'www.thyagoquintas.com.br:3306'; 
$db   = 'engenharia_73';
$user = 'engenharia_73';
$pass = 'preguicadecoleira';
$charset = 'utf8mb4';

$dsn = "mysql:host=$host;dbname=$db;charset=$charset";
$options = [
    PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    PDO::ATTR_EMULATE_PREPARES   => false,
];

try {
     $pdo = new PDO($dsn, $user, $pass, $options);
} catch (\PDOException $e) {
     echo json_encode(["sucesso" => false, "mensagem" => "Erro de conexão DB"]);
     exit;
}

// Recebe os dados via GET (Estilo do Professor)
$categoria = $_GET['categoria'] ?? '';
$quantidade = $_GET['quantidade'] ?? '';
$usuario_id = $_GET['usuario_id'] ?? '';

// Validação simples
if (empty($categoria) || empty($quantidade) || empty($usuario_id)) {
    echo json_encode(["sucesso" => false, "mensagem" => "Dados incompletos."]);
    exit;
}

// Insere no banco
$sql = "INSERT INTO DOACAO (DOACAO_CATEGORIA, DOACAO_QUANTIDADE, USUARIO_ID) VALUES (:cat, :qtd, :uid)";
$stmt = $pdo->prepare($sql);

try {
    $stmt->execute([
        'cat' => $categoria,
        'qtd' => $quantidade,
        'uid' => $usuario_id
    ]);
    echo json_encode(["sucesso" => true, "mensagem" => "Doação registrada com sucesso!"]);
} catch (\PDOException $e) {
    echo json_encode(["sucesso" => false, "mensagem" => "Erro ao registrar: " . $e->getMessage()]);
}
?>