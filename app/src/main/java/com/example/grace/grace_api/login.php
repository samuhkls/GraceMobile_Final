<?php
// 1. Configurações do Banco (MODIFICADO PARA SEU XAMPP LOCAL)
$host = 'www.thyagoquintas.com.br:3306';
$db   = 'engenharia_73';
$user = 'engenharia_73';
$pass = 'preguicadecoleira';
$charset = 'utf8mb4';

// 2. Conexão (EXATAMENTE COMO O PROFESSOR FEZ [cite: 60-70])
$dsn = "mysql:host=$host;dbname=$db;charset=$charset";
$options = [
    PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    PDO::ATTR_EMULATE_PREPARES   => false,
];
try {
     $pdo = new PDO($dsn, $user, $pass, $options);
} catch (\PDOException $e) {
     echo "Erro de conexão: " . $e->getMessage(); // [cite: 88]
     exit;
}

// 3. Lógica do Login (EXATAMENTE COMO O PROFESSOR FEZ [cite: 71-84])
$usuario = $_GET['usuario'] ?? '';
$senha = $_GET['senha'] ?? '';

// Query para verificar as credenciais (comparando senha em texto puro)
$sql = "SELECT USUARIO_ID as usuariold,
               USUARIO_NOME as usuarioNome,
               USUARIO_EMAIL as usuarioEmail,
               USUARIO_CPF as usuarioCpf,
               USUARIO_TIPO as usuarioTipo
        FROM USUARIO
        WHERE USUARIO_EMAIL = :usuario
          AND USUARIO_SENHA = :senha"; // [cite: 79]

$stmt = $pdo->prepare($sql);
$stmt->execute(['usuario' => $usuario, 'senha' => $senha]); // [cite: 81]
$usuarios = $stmt->fetchAll();

header('Content-Type: application/json'); // [cite: 83]
echo json_encode($usuarios); // [cite: 84]

?>