-- Script de inicialização do banco de dados PedidoVenda
-- Este script será executado automaticamente quando o container MySQL for criado

USE pedidovenda;

-- Criar tabela de usuários se não existir
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Inserir usuários padrão se não existirem
INSERT IGNORE INTO usuarios (nome, email, password, role) VALUES
('Administrador', 'admin@pedidovenda.com', 'admin123', 'ADMIN'),
('Usuário Teste', 'user@pedidovenda.com', 'user123', 'USER');

-- Criar outras tabelas necessárias (exemplo básico)
CREATE TABLE IF NOT EXISTS produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10,2) NOT NULL,
    estoque INT DEFAULT 0,
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'PENDENTE',
    valor_total DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Inserir alguns produtos de exemplo
INSERT IGNORE INTO produtos (nome, descricao, preco, estoque) VALUES
('Produto 1', 'Descrição do produto 1', 29.99, 100),
('Produto 2', 'Descrição do produto 2', 49.99, 50),
('Produto 3', 'Descrição do produto 3', 19.99, 200); 