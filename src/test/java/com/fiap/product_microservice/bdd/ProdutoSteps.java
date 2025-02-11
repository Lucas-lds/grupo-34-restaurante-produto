package com.fiap.product_microservice.bdd;

import com.fiap.product_microservice.application.port.out.usecase.ProdutoUseCasePortOut;
import com.fiap.product_microservice.core.domain.Produto;
import com.fiap.product_microservice.infrastructure.adapter.in.ProdutoController;
import com.fiap.product_microservice.infrastructure.adapter.in.request.ProdutoRequest;
import com.fiap.product_microservice.infrastructure.adapter.in.response.ProdutoResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProdutoSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoController produtoController;

    @Autowired
    private ProdutoUseCasePortOut produtoUseCasePortOut;

    @Given("que existem produtos no sistema")
    public void queExistemProdutosNoSistema() {
        Produto produto = new Produto(1L, "Produto Teste", "LANCHE", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg");
        List<Produto> produtos = List.of(produto);
        when(produtoUseCasePortOut.listarProdutos()).thenReturn(produtos);
    }

    @When("eu solicitar a listagem de todos os produtos")
    public void euSolicitarAListagemDeTodosOsProdutos() throws Exception {
        MvcResult result = mockMvc.perform(get("/produtos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", containsString("Produto Teste")))
                .andReturn();

        assertNotNull(result.getResponse());
        assertEquals(200, result.getResponse().getStatus());
    }

    @Then("eu devo receber uma lista contendo todos os produtos")
    public void eu_devo_receber_uma_lista_contendo_todos_os_produtos() throws Exception {
        MvcResult result = mockMvc.perform(get("/produtos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", containsString("Produto Teste")))
                .andReturn();

        assertNotNull(result.getResponse());
        assertEquals(200, result.getResponse().getStatus());
    }

    @Given("que eu tenho os dados de um novo produto")
    public void queEuTenhoOsDadosDeUmNovoProduto() {
        Produto produto = new Produto(1L, "Produto Teste", "LANCHE", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg");
        ProdutoRequest produtoRequest = new ProdutoRequest("Produto Teste", "LANCHE", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg");
        ProdutoResponse produtoResponse = ProdutoResponse.fromDomain(produto);

        when(produtoUseCasePortOut.criarProduto(any(Produto.class))).thenReturn(produto);
    }

    @When("eu solicitar o cadastro do produto")
    public void euSolicitarOCadastroDoProduto() throws Exception {
        String produtoJson = "{\"nome\":\"Produto Teste\", \"categoria\":\"LANCHE\", \"preco\":100.0, \"descricao\":\"Descricao Teste\", \"imagem\":\"http://imagem.url/teste.jpg\"}";

        MvcResult result = mockMvc.perform(post("/produtos/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", containsString("Produto Teste")))
                .andReturn();

        assertNotNull(result.getResponse());
        assertEquals(201, result.getResponse().getStatus());
    }

    @Then("o produto deve ser cadastrado com sucesso")
    public void oProdutoDeveSerCadastradoComSucesso() throws Exception {
    }

    @Given("que um produto existe no sistema")
    public void queUmProdutoExisteNoSistema() throws BadRequestException {
        Produto produto = new Produto(1L, "Produto Teste", "LANCHE", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg");

        when(produtoUseCasePortOut.atualizarProduto(anyLong(), any())).thenReturn(produto);
    }

    @When("eu solicitar a atualização do produto")
    public void euSolicitarAAtualizacaoDoProduto() throws Exception {
        String produtoJson = "{\"nome\":\"Produto Teste\", \"categoria\":\"LANCHE\", \"preco\":150.0, \"descricao\":\"Descricao Teste Atualizada\", \"imagem\":\"http://imagem.url/teste_atualizado.jpg\"}";

        MvcResult result = mockMvc.perform(put("/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", containsString("Produto Teste")))
                .andReturn();

        assertNotNull(result.getResponse());
        assertEquals(200, result.getResponse().getStatus());
    }

    @Then("o produto deve ser atualizado com sucesso")
    public void oProdutoDeveSerAtualizadoComSucesso() {
    }

    @When("eu solicitar a deleção do produto")
    public void euSolicitarADelecaoDoProduto() throws Exception {
        doNothing().when(produtoUseCasePortOut).deletarPorId(anyLong());

        MvcResult result = mockMvc.perform(delete("/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        assertNotNull(result.getResponse());
        assertEquals(204, result.getResponse().getStatus());
    }

    @Then("o produto deve ser deletado com sucesso")
    public void oProdutoDeveSerDeletadoComSucesso() {
    }
}
