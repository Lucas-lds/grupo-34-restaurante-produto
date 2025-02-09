package com.fiap.product_microservice.infrastructure.adapter.in.handler;

import com.fiap.product_microservice.infrastructure.exception.ProdutoNaoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fiap.product_microservice.infrastructure.exception.ProdutoInvalidoException;

@RestControllerAdvice
public class ProdutoExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(ProdutoExceptionHandler.class);
    
    @ExceptionHandler(ProdutoInvalidoException.class)
    public ResponseEntity<ErroRequisicaoResponse> handleInvalidCategoryException(ProdutoInvalidoException ex) {
        logger.error("ProdutoException occurred: {}", ex.getMessage());
        ErroRequisicaoResponse errorResponse = new ErroRequisicaoResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ErroRequisicaoResponse> handle404Error(NoHandlerFoundException ex) {
        logger.error("404 error occurred: {}", ex.getMessage());
        ErroRequisicaoResponse errorResponse = new ErroRequisicaoResponse(HttpStatus.NOT_FOUND.value(), "Produto nao encontrado: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroRequisicaoResponse> handleGenericException(Exception ex) {
        logger.error("An unexpected error occurred: {}", ex.getMessage());
        ErroRequisicaoResponse errorResponse = new ErroRequisicaoResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro inesperado: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
