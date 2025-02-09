package bdd;

import com.fiap.product_microservice.ProductMicroserviceApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = {ProductMicroserviceApplication.class})
public class CucumberSpringConfiguration {
}
