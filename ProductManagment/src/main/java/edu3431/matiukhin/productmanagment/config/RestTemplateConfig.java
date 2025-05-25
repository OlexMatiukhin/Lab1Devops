package edu3431.matiukhin.productmanagment.config;/*
@author sasha
@project microservices-shop
@class RestTemplateConfig
@version 1.0.0
@since 24.04.2025 - 00 - 37
*/


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
