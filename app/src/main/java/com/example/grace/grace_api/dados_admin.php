<?php
header('Content-Type: application/json; charset=UTF-8');

// 1. Configurações (IGUAIS SEMPRE)
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
     echo json_encode(["erro" => "Erro DB"]);
     exit;
}

// 2. Consulta A: Total Geral de Itens (Soma de todas as quantidades)
// Em vez de COUNT(*), usamos SUM(DOACAO_QUANTIDADE)
$sqlTotal = "SELECT SUM(DOACAO_QUANTIDADE) as total_geral FROM DOACAO";
$stmt = $pdo->query($sqlTotal);
$resultadoTotal = $stmt->fetch();
$totalGeral = $resultadoTotal['total_geral'] ?? 0; // Se for null (banco vazio), usa 0

// 3. Contar Solicitações (NOVO: Conta pedidos reais na tabela SOLICITACAO)
$sqlReceptores = "SELECT COUNT(*) as total FROM SOLICITACAO WHERE STATUS = 'Pendente'";
$stmt2 = $pdo->query($sqlReceptores);
$totalReceptores = $stmt2->fetch()['total'];

// 4. Consulta C: Estoque Detalhado por Categoria
// A mágica do GROUP BY: agrupa tudo que tem o mesmo nome e soma
$sqlEstoque = "SELECT DOACAO_CATEGORIA as categoria, SUM(DOACAO_QUANTIDADE) as quantidade 
               FROM DOACAO 
               GROUP BY DOACAO_CATEGORIA
               ORDER BY quantidade DESC"; // Mostra os que tem mais estoque primeiro
$stmt3 = $pdo->query($sqlEstoque);
$listaEstoque = $stmt3->fetchAll();

// 5. Retornar Tudo num JSONzão
echo json_encode([
    "total_itens" => $totalGeral,       // Ex: 150
    "total_solicitacoes" => $totalReceptores,
    "estoque" => $listaEstoque          // Ex: [{"categoria":"Alimentos", "quantidade":50}, ...]
]);
?>