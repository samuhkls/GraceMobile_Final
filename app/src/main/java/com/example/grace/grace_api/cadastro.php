<?php
// 1. Configurações do Banco (igual ao login.php)
$host = 'www.thyagoquintas.com.br:3306';
$db   = 'engenharia_73';
$user = 'engenharia_73';
$pass = 'preguicadecoleira';
$charset = 'utf8mb4';

// 2. Conexão (igual ao login.php [cite: 60-70])
$dsn = "mysql:host=$host;dbname=$db;charset=$charset";
$options = [
    PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    PDO::ATTR_EMULATE_PREPARES   => false,
];
try {
     $pdo = new PDO($dsn, $user, $pass, $options);
} catch (\PDOException $e) {
     echo json_encode(["sucesso" => false, "mensagem" => "Erro de conexão: " . $e->getMessage()]);
     exit;
}

// 3. Lógica do Cadastro (usando $_GET como o professor [cite: 71])
$nome = $_GET['nome'] ?? '';
$email = $_GET['email'] ?? '';
$senha = $_GET['senha'] ?? '';
$cpf = $_GET['cpf'] ?? '';
$tipo = $_GET['tipo'] ?? '';

// Verifica se todos os campos vieram
if (empty($nome) || empty($email) || empty($senha) || empty($cpf) || empty($tipo)) {
    echo json_encode(["sucesso" => false, "mensagem" => "Todos os campos são obrigatórios."]);
    exit;
}

// Query para inserir (salvando senha em texto puro, como o professor)
$sql = "INSERT INTO USUARIO (USUARIO_NOME, USUARIO_EMAIL, USUARIO_SENHA, USUARIO_CPF, USUARIO_TIPO)
        VALUES (:nome, :email, :senha, :cpf, :tipo)";

$stmt = $pdo->prepare($sql);

// Tenta executar
try {
    $stmt->execute([
        'nome' => $nome,
        'email' => $email,
        'senha' => $senha,
        'cpf' => $cpf,
        'tipo' => $tipo
    ]);
    
    // Se der certo, retorna sucesso
    echo json_encode(["sucesso" => true, "mensagem" => "Usuário cadastrado com sucesso!"]);

} catch (\PDOException $e) {
    // Se der erro (ex: email duplicado)
    echo json_encode(["sucesso" => false, "mensagem" => "Erro ao cadastrar: " . $e->getMessage()]);
}

?>