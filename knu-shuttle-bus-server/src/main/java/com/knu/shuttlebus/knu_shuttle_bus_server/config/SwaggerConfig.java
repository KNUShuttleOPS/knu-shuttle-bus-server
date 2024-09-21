package com.knu.shuttlebus.knu_shuttle_bus_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(getInfo());
    }

    private Info getInfo() {
        return new Info()
                .title("KNU Shuttle Bus Application API")
                .description("provides real-time shuttle bus information for Kyungpook National University." +
                    "It allows users to access bus routes, schedules, and track bus locations in real-time," +
                    "making the commuting experience more convenient.")
                .version("1.0.0");
    }
}
