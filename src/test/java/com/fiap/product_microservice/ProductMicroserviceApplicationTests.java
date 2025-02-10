package com.fiap.product_microservice;

import com.fiap.product_microservice.core.domain.Produto;
import com.fiap.product_microservice.infrastructure.adapter.in.validation.ProdutoValidation;
import com.fiap.product_microservice.infrastructure.exception.ProdutoException;
import com.fiap.product_microservice.application.port.out.usecase.ProdutoUseCasePortOut;
import com.fiap.product_microservice.infrastructure.adapter.out.repository.ProdutoRepository;
import com.fiap.product_microservice.infrastructure.adapter.in.ProdutoController;
import com.fiap.product_microservice.infrastructure.adapter.in.request.ProdutoRequest;
import com.fiap.product_microservice.infrastructure.adapter.out.entity.ProdutoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductMicroserviceApplicationTests {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoUseCasePortOut produtoUseCasePortOut;

    @InjectMocks
    private ProdutoController produtoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testProdutoValidation() {
        ProdutoRequest request = new ProdutoRequest("Test Product", "LANCHE", 10.0, "Description", "imageUrl");
        Produto produto = request.toDomain();

        assertTrue(ProdutoValidation.validateFields(produto));
    }

    @Test
    void testProdutoException() {
        Exception exception = assertThrows(ProdutoException.class, () -> {
            throw new ProdutoException("Test Exception");
        });

        assertEquals("Test Exception", exception.getMessage());
    }

    @Test
    void testProdutoUseCase() {
        Produto produto = new Produto(null, "Test Product", "LANCHE", 10.0, "Description", "imageUrl");
        ProdutoEntity produtoEntity = ProdutoEntity.fromDomain(produto);

        when(produtoUseCasePortOut.criarProduto(any(Produto.class))).thenReturn(produto);
        when(produtoRepository.save(any(ProdutoEntity.class))).thenReturn(produtoEntity);

        Produto savedProduto = produtoUseCasePortOut.criarProduto(produto);
        assertNotNull(savedProduto);
        assertEquals("Test Product", savedProduto.getNome());
    }

    // @Test
    // void testProdutoController() {
    //     Produto produto = new Produto(1L, "Test Product", "LANCHE", 10.0, "Description", "imageUrl"); 
    //     ProdutoRequest request = new ProdutoRequest("Test Product", "LANCHE", 10.0, "Description", "imageUrl");

    //     // Configuração do mock
    //     when(produtoUseCasePortOut.criarProduto(any(Produto.class))).thenReturn(produto);

    //     // Ensure the mock is set up correctly
    //     assertNotNull(produto, "Produto should not be null");

    //     ResponseEntity<ProdutoResponse> responseEntity = produtoController.cadastrarProduto(request);

    //     System.out.println("Resposta do Controller: " + responseEntity);

    //     assertNotNull(responseEntity);
    //     assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    //     assertNotNull(responseEntity.getBody());
    //     assertEquals(1L, responseEntity.getBody().idProduto());
    // }
}
