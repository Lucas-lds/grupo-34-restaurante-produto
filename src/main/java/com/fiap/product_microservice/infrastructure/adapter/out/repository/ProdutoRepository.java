package com.fiap.product_microservice.infrastructure.adapter.out.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.product_microservice.infrastructure.adapter.out.entity.ProdutoEntity;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    List<ProdutoEntity> findProductByCategoria(String categoria);
}
