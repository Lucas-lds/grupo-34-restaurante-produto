package com.fiap.product_microservice.bdd;

import com.fiap.product_microservice.ProductMicroserviceApplication;
import com.fiap.product_microservice.application.port.out.usecase.ProdutoUseCasePortOut;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = ProductMicroserviceApplication.class)
@MockBean(ProdutoUseCasePortOut.class)
public class SpringGlue {
}
