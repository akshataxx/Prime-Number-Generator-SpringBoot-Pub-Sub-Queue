package com.example.assignment2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

//class provides configuration for `RestTemplate` to send HTTP requests
//annotation provides bean definitions and configurations for the RestTemplate
@Configuration
public class RestTemplateConfig {

    //retrieve the values from the application properties file
    @Value("${spring.security.user.name}")
    private String rpcUsername;

    @Value("${spring.security.user.password}")
    private String rpcPassword;

    @Value("${client.rpc.url}")
    private String rpcUrl;

    /*bean method creates an instance of RestTemplate and adds a BasicAuthenticationInterceptor
    to its interceptors list.This interceptor provides basic authentication by setting the provided
    username and password in the Authorization header of the outgoing HTTP requests.
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(rpcUsername, rpcPassword));
        return restTemplate;
    }

    //bean method returns the value of the rpcUrl property
    @Bean
    public String rpcEndpointUrl() {
        return rpcUrl;
    }
}
