package com.example.assignment2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

/**
 * Class to provide bean definition and configurations for RestTemplate to send HTTP requests to RPC server
 * @Configuration annotation provides bean definitions and configurations for the RestTemplate
 */
@Configuration
public class RestTemplateConfig {

    //retrieve the values from the application properties file
    @Value("${spring.security.user.name}")
    private String rpcUsername;

    @Value("${spring.security.user.password}")
    private String rpcPassword;

    @Value("${client.rpc.url}")
    private String rpcUrl;

    /**
     * Method to create a new RestTemplate instance and add a BasicAuthenticationInterceptor to its interceptors list
     * This interceptor provides basic authentication by setting the provided username and password in the Authorization header of the outgoing HTTP requests
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(rpcUsername, rpcPassword));
        return restTemplate;
    }

    /**
     * Method to return the value of the rpcUrl property
     * @return String
     */
    @Bean
    public String rpcEndpointUrl() {
        return rpcUrl;
    }
}
