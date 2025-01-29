package com.fiap.product_microservice.infrastructure.configuration;

import com.fiap.product_microservice.application.port.out.ProdutoServicePortOut;
import com.fiap.product_microservice.application.port.out.usecase.ProdutoUseCasePortOut;
import com.fiap.product_microservice.core.usecase.ProdutoUseCase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
  
  @Bean
    public ProdutoUseCasePortOut produtoUseCase(ProdutoServicePortOut produtoService) {
        return new ProdutoUseCase(produtoService);
    }

}