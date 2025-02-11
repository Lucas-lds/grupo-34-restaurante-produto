package com.fiap.product_microservice.infrastructure.adapter.out;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fiap.product_microservice.core.domain.Produto;
import com.fiap.product_microservice.infrastructure.adapter.out.entity.ProdutoEntity;
import com.fiap.product_microservice.infrastructure.adapter.out.repository.ProdutoRepository;
import com.fiap.product_microservice.infrastructure.exception.ProdutoInvalidoException;
import com.fiap.product_microservice.infrastructure.exception.ProdutoNaoEncontradoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProdutoAdapterOutTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoAdapterOut produtoAdapterOut;

    @Test
    void listarProdutos_DeveRetornarListaDeProdutos() {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        when(produtoRepository.findAll()).thenReturn(Collections.singletonList(produtoEntity));

        List<Produto> produtos = produtoAdapterOut.listarProdutos();

        assertNotNull(produtos);
        assertFalse(produtos.isEmpty());
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    void deveCriarProdutoComSucesso() {
        Produto produto = new Produto(
                1L,
                "Produto Teste",
                "LANCHE",
                100.0,
                "Descrição Teste",
                "http://imagem.url/teste.jpg"
        );
        ProdutoEntity produtoEntity = new ProdutoEntity();
        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

        Produto produtoCriado = produtoAdapterOut.criarProduto(produto);

        assertNotNull(produtoCriado);
        verify(produtoRepository, times(1)).save(any(ProdutoEntity.class));
    }

    @Test
    void deveBuscarProdutoComSucesso() {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));

        Produto produto = produtoAdapterOut.buscarPorId(1L);

        assertNotNull(produto);
        verify(produtoRepository, times(1)).findById(anyLong());
    }

    @Test
    void deveLancarExececaoQuandoNaoEncontrarProduto() {
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ProdutoNaoEncontradoException.class, () -> produtoAdapterOut.buscarPorId(1L));
        verify(produtoRepository, times(1)).findById(anyLong());
    }

    @Test
    void deveDeletarProdutoComSucesso() {
        produtoAdapterOut.deletarPorId(1L);
        verify(produtoRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deveAtualizarComSucesso() {
        Produto produto = new Produto(
                1L,
                "Produto Teste",
                "LANCHE",
                100.0,
                "Descrição Teste",
                "http://imagem.url/teste.jpg"
        );
        ProdutoEntity produtoEntity = new ProdutoEntity();
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

        Produto produtoAtualizado = produtoAdapterOut.atualizarProduto(1L, produto);

        assertNotNull(produtoAtualizado);
        verify(produtoRepository, times(1)).findById(anyLong());
        verify(produtoRepository, times(1)).save(any(ProdutoEntity.class));
    }

    @Test
    void atualizarProdutoDeveLancarExcecaoQuandoNaoEncontrado() {
        Produto produto = new Produto(
                1L,
                "Produto Teste",
                "LANCHE",
                100.0,
                "Descrição Teste",
                "http://imagem.url/teste.jpg"
        );
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> produtoAdapterOut.atualizarProduto(1L, produto));
        verify(produtoRepository, times(1)).findById(anyLong());
        verify(produtoRepository, times(0)).save(any(ProdutoEntity.class));
    }

    @Test
    void produtoInvalidoProdutoDeveLancarExcecao() {
        Produto produto = new Produto(
                1L,
                "Produto Teste",
                "aaa",
                100.0,
                "Descrição Teste",
                "http://imagem.url/teste.jpg"
        );
        ProdutoEntity produtoEntity = new ProdutoEntity();
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produtoEntity));

        assertThrows(ProdutoInvalidoException.class, () -> produtoAdapterOut.atualizarProduto(1L, produto));
    }

    @Test
    void listarProdutoPorCategoriaDeveRetornarListaDeProdutos() {
        String categoria = "LANCHE";
        ProdutoEntity produtoEntity = new ProdutoEntity("Produto Teste", categoria, 10.0, "Descrição Teste", "http://imagem.url/teste.jpg");
        when(produtoRepository.findProductByCategoria(anyString())).thenReturn(Collections.singletonList(produtoEntity));

        List<Produto> produtos = produtoAdapterOut.listarProdutoPorCategoria(categoria);

        assertNotNull(produtos);
        assertFalse(produtos.isEmpty());
        assertEquals(1, produtos.size());
        assertEquals(categoria, produtos.get(0).getCategoria());
        verify(produtoRepository, times(1)).findProductByCategoria(anyString());
    }

}
