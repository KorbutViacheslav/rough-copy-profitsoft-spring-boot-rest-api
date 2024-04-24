package ua.profitsoft.roughcopyprofitsoftspringbootrestapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Book API",
        version = "1.0.0",
        description = "Spring Boot RESTful service for managing books and authors."))
public class RoughCopyProfitsoftSpringBootRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoughCopyProfitsoftSpringBootRestApiApplication.class, args);
    }

}
