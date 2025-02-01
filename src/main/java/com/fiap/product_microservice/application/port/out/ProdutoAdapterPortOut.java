package com.fiap.product_microservice.application.port.out;

import java.util.List;

import com.fiap.product_microservice.core.domain.Produto;

public interface ProdutoAdapterPortOut {

    List<Produto> listarProdutos();

    List<Produto> listarProdutoPorCategoria(String categoria);

    Produto criarProduto(Produto produto);

    Produto atualizarProduto(Long id, Produto produto);

    void deletarPorId(Long id);

    Produto buscarPorId(Long id);
}
