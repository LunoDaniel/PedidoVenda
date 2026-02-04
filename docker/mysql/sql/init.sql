-- Criação da estrutura básica do banco
CREATE TABLE IF NOT EXISTS usuario (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    grupo VARCHAR(50) NOT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insere um usuário admin inicial
INSERT IGNORE INTO usuario (email, senha, grupo)
VALUES ('admin@pedidovenda.com', '$2a$10$xJwL5v.n7U4X7Q8f6Qz3u.Fq.7U9sZwjJZ8dD8Q7Jv6nV1mY6WX1O', 'ADMIN');