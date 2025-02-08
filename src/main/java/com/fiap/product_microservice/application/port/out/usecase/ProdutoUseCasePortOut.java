package com.fiap.product_microservice.application.port.out.usecase;

import java.util.List;
import org.apache.coyote.BadRequestException;

import com.fiap.product_microservice.core.domain.Produto;

public interface ProdutoUseCasePortOut {
    List<Produto> listarProdutos();

    List<Produto> listarProdutoPorCategoria(String categoria);

    Produto criarProduto(Produto produto);

    Produto atualizarProduto(Long id, Produto produto) throws BadRequestException;

    void deletarPorId(Long id);

    Produto buscarPorid(Long id);

}
