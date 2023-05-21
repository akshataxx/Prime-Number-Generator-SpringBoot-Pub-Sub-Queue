package com.example.assignment2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Class configures basic authentication for the REST endpoints
 * Indicates that it provides bean definitions and configurations
 */
@Configuration
public class SecurityConfig {

    /**
     * Method creates and configures the SecurityFilterChain
     * Sets up authorization rules to allow access to the /api/answers/** endpoint only for authenticated users
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

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

    /**
     * Method creates an InMemoryUserDetailsManager and adds an in-memory user with the username "admin" and
     * password "admin", with the role "ADMIN"
     * @return
     */
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
