package bdd;

import com.fiap.product_microservice.core.domain.Produto;
import com.fiap.product_microservice.infrastructure.adapter.in.ProdutoController;
import com.fiap.product_microservice.infrastructure.adapter.in.request.ProdutoRequest;
import com.fiap.product_microservice.infrastructure.adapter.in.response.ProdutoResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = CucumberSpringConfiguration.class)
public class ProdutoSteps {

    @Autowired
    private ProdutoController produtoController;

    private Produto produto;
    private ProdutoRequest produtoRequest;
    private ProdutoResponse produtoResponse;
    private ResponseEntity<?> responseEntity;

    @Given("que existem produtos no sistema")
    public void queExistemProdutosNoSistema() {
        produto = new Produto(1L, "Produto Teste", "LANCHE", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg");
        produtoController.cadastrarProduto(new ProdutoRequest("Produto Teste", "LANCHE", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg"));
    }

    @When("eu solicitar a listagem de todos os produtos")
    public void euSolicitarAListagemDeTodosOsProdutos() {
        responseEntity = ResponseEntity.ok(produtoController.listarProdutos());
    }

    @Then("eu devo receber uma lista contendo todos os produtos")
    public void euDevoReceberUmaListaContendoTodosOsProdutos() {
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertFalse(((List<?>) responseEntity.getBody()).isEmpty());
    }

    @Given("que eu tenho os dados de um novo produto")
    public void queEuTenhoOsDadosDeUmNovoProduto() {
        produtoRequest = new ProdutoRequest("Produto Teste", "LANCHE", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg");
        produto = produtoRequest.toDomain();
        produtoResponse = ProdutoResponse.fromDomain(produto);
    }

    @When("eu solicitar o cadastro do produto")
    public void euSolicitarOCadastroDoProduto() {
        responseEntity = produtoController.cadastrarProduto(produtoRequest);
    }

    @Then("o produto deve ser cadastrado com sucesso")
    public void oProdutoDeveSerCadastradoComSucesso() {
        assertNotNull(responseEntity);
        assertEquals(201, responseEntity.getStatusCodeValue());
        ProdutoResponse responseBody = (ProdutoResponse) responseEntity.getBody();
        assertEquals(produtoResponse.nome(), responseBody.nome());
    }

    @Given("que um produto existe no sistema")
    public void queUmProdutoExisteNoSistema() {
        produto = new Produto(1L, "Produto Teste", "LANCHE", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg");
        responseEntity = produtoController.cadastrarProduto(new ProdutoRequest("Produto Teste", "LANCHE", 100.0, "Descricao Teste", "http://imagem.url/teste.jpg"));
    }

    @When("eu solicitar a atualização do produto")
    public void euSolicitarAAtualizacaoDoProduto() throws Exception {
        produtoRequest = new ProdutoRequest("Produto Teste Atualizado", "LANCHE", 150.0, "Descricao Teste Atualizada", "http://imagem.url/teste_atualizado.jpg");
        responseEntity = produtoController.atualizarProduto(1L, produtoRequest);
    }

    @Then("o produto deve ser atualizado com sucesso")
    public void oProdutoDeveSerAtualizadoComSucesso() {
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        ProdutoResponse responseBody = (ProdutoResponse) responseEntity.getBody();
        assertEquals(produtoRequest.nome(), responseBody.nome());
    }

    @When("eu solicitar a deleção do produto")
    public void euSolicitarADelecaoDoProduto() {
        responseEntity = produtoController.deletarPorId(1L);
    }

    @Then("o produto deve ser deletado com sucesso")
    public void oProdutoDeveSerDeletadoComSucesso() {
        assertNotNull(responseEntity);
        assertEquals(204, responseEntity.getStatusCodeValue());
    }
}
