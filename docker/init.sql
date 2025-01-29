-- ==============================
-- TABELAS DO BANCO DE DADOS
-- ==============================

-- Se precisar alterar a estrutura da tabela, utilize comandos ALTER TABLE.
-- Exemplos:
-- ALTER TABLE tb_clientes ADD COLUMN endereco VARCHAR(255);
-- ALTER TABLE tb_produtos MODIFY COLUMN preco DECIMAL(12, 2);

CREATE TABLE IF NOT EXISTS tb_produtos (
    id_produto BIGINT PRIMARY KEY AUTO_INCREMENT,  
    nome VARCHAR(100) NOT NULL,               
    categoria TEXT NOT NULL,                  
    preco DECIMAL(10, 2) NOT NULL,            
    descricao TEXT,                           
    imagem_url VARCHAR(255)                   
);
