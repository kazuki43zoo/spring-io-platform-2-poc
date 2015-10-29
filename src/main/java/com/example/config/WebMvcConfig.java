package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**"); // (1)
        registry.addMapping("/api/**")
                .allowCredentials(false)
                .allowedHeaders("")
                .allowedMethods("")
                .allowedOrigins("")
                .maxAge(10)
                .exposedHeaders("");
    }
}