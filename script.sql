-- Modelo físico (MySQL) - Gestão de Estoque de Veículos
-- Opcional: o Spring Boot (ddl-auto=update) já cria as tabelas sozinho.

CREATE DATABASE IF NOT EXISTS estoque_veiculos;
USE estoque_veiculos;

CREATE TABLE IF NOT EXISTS marcas (
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS modelos (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    marca_id BIGINT NOT NULL,
    CONSTRAINT fk_modelo_marca FOREIGN KEY (marca_id) REFERENCES marcas (id)
);

CREATE TABLE IF NOT EXISTS veiculos (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    modelo_id     BIGINT NOT NULL,
    ano           INT NOT NULL,
    cor           VARCHAR(50) NOT NULL,
    preco         DECIMAL(12,2) NOT NULL,
    quilometragem INT NOT NULL DEFAULT 0,
    status        VARCHAR(20) NOT NULL DEFAULT 'DISPONIVEL',
    CONSTRAINT fk_veiculo_modelo FOREIGN KEY (modelo_id) REFERENCES modelos (id)
);

-- Dados de exemplo
INSERT INTO marcas (nome) VALUES ('Toyota'), ('Honda'), ('Volkswagen');

INSERT INTO modelos (nome, marca_id) VALUES
    ('Corolla', 1), ('Hilux', 1), ('Civic', 2), ('Gol', 3), ('Polo', 3);

INSERT INTO veiculos (modelo_id, ano, cor, preco, quilometragem, status) VALUES
    (1, 2021, 'Prata',  115000.00, 32000, 'DISPONIVEL'),
    (2, 2020, 'Branca', 210000.00, 58000, 'DISPONIVEL'),
    (3, 2019, 'Preto',   98000.00, 61000, 'RESERVADO'),
    (4, 2018, 'Vermelho', 42000.00, 80000, 'VENDIDO'),
    (5, 2022, 'Azul',    85000.00, 15000, 'DISPONIVEL');
