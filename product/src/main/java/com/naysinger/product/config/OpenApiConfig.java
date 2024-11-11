package com.naysinger.product.config;

import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(ServletContext servletContext) {
        Server server = new Server().url(servletContext.getContextPath());

        return new OpenAPI()
                .info(new Info().title("Products API")
                        .description(
                                "API de Produtos.")
                        .version("v1.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .servers(List.of(server));
    }
}
