<?php
// listar_doacoes.php

// 1. Configurações do Banco (IGUALZINHO AOS OUTROS)
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
     // Se der erro de conexão, devolve lista vazia para não crashar o app
     echo json_encode([]);
     exit;
}

// 2. A Consulta SQL
// Selecionamos tudo da tabela DOACAO e ordenamos pela data (mais novos primeiro)
// JOIN opcional: Se quiser mostrar o NOME do doador, faríamos um JOIN com USUARIO aqui.
// Por enquanto, vamos pegar os dados da doação mesmo.
$sql = "SELECT DOACAO_ID, DOACAO_CATEGORIA, DOACAO_QUANTIDADE, DATA_DOACAO 
        FROM DOACAO 
        ORDER BY DOACAO_ID DESC";

try {
    $stmt = $pdo->query($sql);
    $doacoes = $stmt->fetchAll();
    
    // 3. Devolve o JSON
    header('Content-Type: application/json');
    echo json_encode($doacoes);

} catch (\PDOException $e) {
    echo json_encode([]);
}
?>