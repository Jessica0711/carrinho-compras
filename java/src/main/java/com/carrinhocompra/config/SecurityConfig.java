package com.carrinhocompra.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private static final String CARRINHO_COMPRAS = "/carrinho-compras";

	private static final String PRODUTOS = "/produtos";
	
	@Autowired
    SecurityFilter securityFilter;

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(POST, "/auth/login").permitAll()
                        .requestMatchers(POST, "/auth/registrar").permitAll()
                        .requestMatchers(POST, PRODUTOS).hasRole("ADMIN")
                        .requestMatchers(PUT, PRODUTOS).hasRole("ADMIN")
                        .requestMatchers(GET, PRODUTOS).permitAll()
                        .requestMatchers(GET, CARRINHO_COMPRAS + "/**").permitAll()
                        .requestMatchers(POST, CARRINHO_COMPRAS + "/produtos/**").permitAll()
                        .requestMatchers(PUT, CARRINHO_COMPRAS).hasRole("USER")
                        .requestMatchers(DELETE, CARRINHO_COMPRAS + "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
