package com.fiap.product_microservice.infrastructure.adapter.in.request;

import com.fiap.product_microservice.core.domain.Produto;

public record ProdutoRequest(String nome, String categoria, Double preco, String descricao, String imagemUrl) {
    
    public Produto toDomain() {
        return new Produto(null, nome, categoria, preco, descricao, imagemUrl);
    }
}
