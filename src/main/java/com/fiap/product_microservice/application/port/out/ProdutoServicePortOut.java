package com.fiap.product_microservice.application.port.out;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.fiap.product_microservice.core.domain.Produto;

public interface ProdutoServicePortOut {
    List<Produto> listarProdutos();

    List<Produto> listarProdutoPorCategoria(String categoria);

    Produto criarProduto(Produto produto);

    Produto atualizarProduto(Long id, Produto produto) throws BadRequestException;

    void deletarPorId(Long id);

    Produto buscarPorId(Long id);
}
