package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.TaskExecutorRegistration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
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

    // (2)
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(5000);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        configurer.setTaskExecutor(executor);
    }

}