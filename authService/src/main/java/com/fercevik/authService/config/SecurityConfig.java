package com.fercevik.authService.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    public static final String ADMIN = "admin";
    public static final String USER = "user";
    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
           .authorizeHttpRequests(auth -> auth
           .requestMatchers(HttpMethod.POST, "/login", "/login/**").permitAll()
           .requestMatchers(HttpMethod.GET, "/test/anonymous", "/test/anonymous/**").permitAll()
           .requestMatchers(HttpMethod.GET, "/test/admin", "/test/admin/**").hasRole(ADMIN)
           .requestMatchers(HttpMethod.GET, "/test/user", "/test/user/**").hasRole(USER)
           .anyRequest().authenticated()
       );
       http.oauth2ResourceServer(ors -> ors
           .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
       );
       http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
       return http.build();
    }
}
