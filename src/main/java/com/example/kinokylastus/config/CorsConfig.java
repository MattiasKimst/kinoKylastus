package com.example.kinokylastus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    //otherwise CORS policy is blocking the request from Vue.js frontend
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080") // Add the origin URL of your Vue.js frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Add the HTTP methods allowed for CORS requests
                .allowedHeaders("*"); // Add the allowed request headers
    }
}

