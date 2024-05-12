package com.example.apexcrud.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER="Authorization";

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(new Info().title("APEX CRUD")
                        .description("APEX CRUD Application: Backend Code\",\"This code is developed by- Md. Al Shariar")
                        .version("1.0")
                        .contact(new Contact().name("Md. Al Shariar").email("itmasjoy@gmail.com")));
    }
}