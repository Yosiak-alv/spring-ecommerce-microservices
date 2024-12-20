package com.ecommerce.paymentservice.configuration;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("product-service")
                .addOperationCustomizer(customGlobalHeaders())
                .packagesToScan("com.ecommerce.paymentservice.controller",
                        "com.ecommerce.paymentservice.domain.dto")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Product Service API")
                        .description("Service Integration for external product service.")
                        .version("v1.0.0")
                        .license(new License().name("Josias").url("https://josiasalvarenga.com")))
                .externalDocs(new ExternalDocumentation());
    }

    public OperationCustomizer customGlobalHeaders() {

        return (Operation operation, HandlerMethod handlerMethod) -> {

            io.swagger.v3.oas.models.parameters.Parameter headerParam = new io.swagger.v3.oas.models.parameters.Parameter()
                    .in(ParameterIn.HEADER.toString())
                    .schema(new StringSchema())
                    .name("trace-id")
                    .description("Unique request id")
                    .required(false);

            operation.addParametersItem(headerParam);

            return operation;
        };
    }
}
