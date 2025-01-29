package com.fiap.product_microservice.application.service;

import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import com.fiap.product_microservice.application.port.out.ProdutoAdapterPortOut;
import com.fiap.product_microservice.application.port.out.ProdutoServicePortOut;
import com.fiap.product_microservice.core.domain.Produto;

@Service
public class ProdutoService implements ProdutoServicePortOut{

    private final ProdutoAdapterPortOut produtoAdapterPortOut;

    public ProdutoService( ProdutoAdapterPortOut produtoAdapterPortOut) {
        this.produtoAdapterPortOut = produtoAdapterPortOut;
    }

    @Override
    public List<Produto> listarProdutos() {
        return produtoAdapterPortOut.listarProdutos();
    }

    @Override
    public List<Produto> listarProdutoPorCategoria(String categoria) {
        return produtoAdapterPortOut.listarProdutoPorCategoria(categoria);
    }

    @Override
    public Produto criarProduto(Produto produto) {
        return produtoAdapterPortOut.criarProduto(produto);
    }

    @Override
    public Produto atualizarProduto(Long id, Produto produto) throws BadRequestException {
        return produtoAdapterPortOut.atualizarProduto(id, produto);
    }

    @Override
    public void deletarPorId(Long id) {
        produtoAdapterPortOut.deletarPorId(id);
    }
}
