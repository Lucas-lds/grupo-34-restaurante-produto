package com.fiap.product_microservice.core.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fiap.product_microservice.application.port.out.ProdutoServicePortOut;
import com.fiap.product_microservice.core.domain.Produto;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProdutoUseCaseTest {

    @Mock
    private ProdutoServicePortOut produtoServicePortOut;

    @InjectMocks
    private ProdutoUseCase produtoUseCase;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto(1L, "Produto Teste", "LANCHE", 10.0, "Descrição Teste", "http://imagem.url/teste.jpg");
    }

    @Test
    void listarProdutosDeveRetornarListaDeProdutos() {
        when(produtoServicePortOut.listarProdutos()).thenReturn(Collections.singletonList(produto));

        List<Produto> produtos = produtoUseCase.listarProdutos();

        assertNotNull(produtos);
        assertFalse(produtos.isEmpty());
        assertEquals(1, produtos.size());
        verify(produtoServicePortOut, times(1)).listarProdutos();
    }

    @Test
    void listarProdutoPorCategoriaDeveRetornarListaDeProdutos() {
        when(produtoServicePortOut.listarProdutoPorCategoria(anyString())).thenReturn(Collections.singletonList(produto));

        List<Produto> produtos = produtoUseCase.listarProdutoPorCategoria("LANCHE");

        assertNotNull(produtos);
        assertFalse(produtos.isEmpty());
        assertEquals(1, produtos.size());
        verify(produtoServicePortOut, times(1)).listarProdutoPorCategoria(anyString());
    }

    @Test
    void criarProdutoDeveCadastrarProdutoComSucesso() {
        when(produtoServicePortOut.criarProduto(any(Produto.class))).thenReturn(produto);

        Produto produtoCriado = produtoUseCase.criarProduto(produto);

        assertNotNull(produtoCriado);
        assertEquals(produto.getIdProduto(), produtoCriado.getIdProduto());
        verify(produtoServicePortOut, times(1)).criarProduto(any(Produto.class));
    }

    @Test
    void atualizarProdutoDeveAtualizarProdutoComSucesso() throws BadRequestException {
        when(produtoServicePortOut.atualizarProduto(anyLong(), any(Produto.class))).thenReturn(produto);

        Produto produtoAtualizado = produtoUseCase.atualizarProduto(1L, produto);

        assertNotNull(produtoAtualizado);
        assertEquals(produto.getIdProduto(), produtoAtualizado.getIdProduto());
        verify(produtoServicePortOut, times(1)).atualizarProduto(anyLong(), any(Produto.class));
    }

    @Test
    void deletarPorIdDeveDeletarProdutoComSucesso() {
        doNothing().when(produtoServicePortOut).deletarPorId(anyLong());

        produtoUseCase.deletarPorId(1L);

        verify(produtoServicePortOut, times(1)).deletarPorId(anyLong());
    }

    @Test
    void buscarPorIdDeveRetornarProdutoPorId() {
        when(produtoServicePortOut.buscarPorId(anyLong())).thenReturn(produto);

        Produto produtoEncontrado = produtoUseCase.buscarPorid(1L);

        assertNotNull(produtoEncontrado);
        assertEquals(produto.getIdProduto(), produtoEncontrado.getIdProduto());
        verify(produtoServicePortOut, times(1)).buscarPorId(anyLong());
    }
}
