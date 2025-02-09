package com.fiap.product_microservice.infrastructure.adapter.in;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fiap.product_microservice.application.port.out.usecase.ProdutoUseCasePortOut;
import com.fiap.product_microservice.core.domain.Produto;
import com.fiap.product_microservice.infrastructure.adapter.in.request.ProdutoRequest;
import com.fiap.product_microservice.infrastructure.adapter.in.response.ProdutoResponse;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTest {

    @Mock
    private ProdutoUseCasePortOut produtoUseCasePortOut;

    @InjectMocks
    private ProdutoController produtoController;

    private Produto produto;
    private ProdutoRequest produtoRequest;
    private ProdutoResponse produtoResponse;

    @BeforeEach
    void setUp() {
        produto = new Produto(1L, "Produto Teste", "LANCHE", 10.0, "Descrição Teste", "http://imagem.url/teste.jpg");
        produtoRequest = new ProdutoRequest("Produto Teste", "LANCHE", 10.0, "Descrição Teste", "http://imagem.url/teste.jpg");
        produtoResponse = new ProdutoResponse(1L, "Produto Teste", "LANCHE", 10.0, "Descrição Teste", "http://imagem.url/teste.jpg");
    }

    @Test
    void listarProdutosDeveRetornarListaDeProdutos() {
        when(produtoUseCasePortOut.listarProdutos()).thenReturn(Collections.singletonList(produto));

        List<Produto> produtos = produtoController.listarProdutos();

        assertNotNull(produtos);
        assertFalse(produtos.isEmpty());
        assertEquals(1, produtos.size());
        verify(produtoUseCasePortOut, times(1)).listarProdutos();
    }

    @Test
    void buscarPorCategoriaDeveRetornarListaDeProdutos() {
        when(produtoUseCasePortOut.listarProdutoPorCategoria(anyString())).thenReturn(Collections.singletonList(produto));

        List<Produto> produtos = produtoController.buscarPorCategoria("LANCHE");

        assertNotNull(produtos);
        assertFalse(produtos.isEmpty());
        assertEquals(1, produtos.size());
        verify(produtoUseCasePortOut, times(1)).listarProdutoPorCategoria(anyString());
    }

    @Test
    void cadastrarProdutoDeveCadastrarProdutoComSucesso() {
        when(produtoUseCasePortOut.criarProduto(any(Produto.class))).thenReturn(produto);

        ResponseEntity<ProdutoResponse> responseEntity = produtoController.cadastrarProduto(produtoRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(produtoResponse.idProduto(), responseEntity.getBody().idProduto());
        verify(produtoUseCasePortOut, times(1)).criarProduto(any(Produto.class));
    }

    @Test
    void atualizarProdutoDeveAtualizarProdutoComSucesso() throws BadRequestException {
        when(produtoUseCasePortOut.atualizarProduto(anyLong(), any(Produto.class))).thenReturn(produto);

        ResponseEntity<ProdutoResponse> responseEntity = produtoController.atualizarProduto(1L, produtoRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(produtoResponse.idProduto(), responseEntity.getBody().idProduto());
        verify(produtoUseCasePortOut, times(1)).atualizarProduto(anyLong(), any(Produto.class));
    }

    @Test
    void deletarPorIdDeveDeletarProdutoComSucesso() {
        doNothing().when(produtoUseCasePortOut).deletarPorId(anyLong());

        ResponseEntity<Void> responseEntity = produtoController.deletarPorId(1L);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(produtoUseCasePortOut, times(1)).deletarPorId(anyLong());
    }

    @Test
    void buscarPorIdDeveRetornarProdutoPorId() {
        when(produtoUseCasePortOut.buscarPorid(anyLong())).thenReturn(produto);

        ResponseEntity<ProdutoResponse> responseEntity = produtoController.buscarPorId(1L);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(produtoResponse.idProduto(), responseEntity.getBody().idProduto());
        verify(produtoUseCasePortOut, times(1)).buscarPorid(anyLong());
    }
}
