package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // tillater alle endepunkter
                        .allowedOrigins("http://localhost:3000") // tillater kun forespørsler fra localhost:3000
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // tillater nødvendige metoder
                        .allowedHeaders("*") // tillater alle headere
                        .allowCredentials(true); // tillater cookie-baserte autentiseringer, hvis nødvendig
            }
        };
    }
}
