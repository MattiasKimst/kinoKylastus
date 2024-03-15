package com.example.kinokylastus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    //muidu CORS policy blokeerib  Vue.js frontendist tehtud päringuid
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080") // front end jookseb 8080 pordis
                .allowedMethods("GET", "POST", "PUT", "DELETE") // milliseid REST päringuid lubame
                .allowedHeaders("*");
    }
}

