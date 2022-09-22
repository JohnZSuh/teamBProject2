package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
@ComponentScan("com.Nick")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public ObjectMapper jsonMapper() {
        return new ObjectMapper();
    }
    
}
