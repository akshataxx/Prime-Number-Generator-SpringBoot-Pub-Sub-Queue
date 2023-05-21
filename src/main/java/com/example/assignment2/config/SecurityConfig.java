package com.example.assignment2.config;

import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/*import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;*/
//import org.springframework.security.config.annotation.web.configurers.HttpSecurityBuilder;
//import org.springframework.security.config.annotation.web.configurers.SecurityConfigurer;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//class configures basic authentication for the REST endpoints
// indicate that it provides bean definitions and configurations
@Configuration
public class SecurityConfig {

    //creates and configures the SecurityFilterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*sets up authorization rules to allow access to the /api/answers/** endpoint only
        for authenticated users using HTTP Basic authentication */
        //lambda expression in requestMatchers to configure authorization rules and specify the URL pattern
        //httpBasic method is called directly after the authorizeRequests method to enable HTTP Basic auth

        http    .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/answers/**").hasRole("ADMIN")
                .anyRequest().authenticated())
                .httpBasic();
        http.csrf().disable();
        //csrf().disable() method is called separately to disable CSRF protection

        //method returns the SecurityFilterChain instance
        return http.build();
    }

    //method creates an InMemoryUserDetailsManager and adds an in-memory user with the username "admin" and
    // password "admin", with the role "ADMIN"
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.withUsername("admin")
                .password("{noop}admin")
                .roles("ADMIN")
                .build());
        return userDetailsManager;
    }
}
