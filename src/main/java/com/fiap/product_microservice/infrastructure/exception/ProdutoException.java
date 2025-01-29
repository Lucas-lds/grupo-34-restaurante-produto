package com.fiap.product_microservice.infrastructure.exception;

public class ProdutoException extends RuntimeException {
    
    public ProdutoException(String message) {
        super(message);
    }
}