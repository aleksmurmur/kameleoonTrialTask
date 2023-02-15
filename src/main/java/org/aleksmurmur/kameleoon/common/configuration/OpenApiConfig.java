package org.aleksmurmur.kameleoon.common.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.aleksmurmur.kameleoon.api.Paths.API_V1_PATH;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addServersItem(new Server().url("/"));
    }

    @Bean
    OpenAPI userOpenApi() {
        return new OpenAPI().addServersItem(new Server().url(API_V1_PATH));
    }
}
