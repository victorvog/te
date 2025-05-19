package com.biopark.cepex.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a todos os paths da sua API
                .allowedOrigins(
                        "http://localhost:3000", // Para Create React App
                        "http://localhost:5173"  // Para Vite
                        // Adicione outras origens se necessário, ex: sua URL de produção do frontend
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permite todos os headers na requisição
                .allowCredentials(true); // Permite o envio de cookies e informações de autenticação
    }
}