package com.fercevik.gateway.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private KeycloakJwtRolesConverter converter;
    public static final String ADMIN = "ROLE_gateway_admin";
    public static final String USER = "ROLE_gateway_user";

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {

        var jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                new ReactiveJwtGrantedAuthoritiesConverterAdapter(converter)
        );

        return jwtAuthenticationConverter;
    }
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(auth -> auth
           .pathMatchers(HttpMethod.GET, "/login", "/login/**").permitAll()
           .pathMatchers(HttpMethod.GET, "/public", "/public/**").permitAll()
           .pathMatchers(HttpMethod.GET, "/test/admin", "/test/admin/**").hasAuthority(ADMIN)
           .pathMatchers(HttpMethod.GET, "/test/user", "/test/user/**").hasAuthority(USER)
           .anyExchange().authenticated()
       );
       http.oauth2ResourceServer(ors -> ors
           .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
       );
       return http.build();
    }

}
