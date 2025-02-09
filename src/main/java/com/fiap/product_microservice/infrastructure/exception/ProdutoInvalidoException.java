package com.fiap.product_microservice.infrastructure.exception;

public class ProdutoInvalidoException extends RuntimeException {
    
    public ProdutoInvalidoException(String message) {
        super(message);
    }
}