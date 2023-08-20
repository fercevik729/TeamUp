package com.fercevik.gateway.config;

import com.fercevik.tokenlib.KeycloakJwtRolesConverter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(auth -> auth
               .pathMatchers("/oauth2/**").permitAll()
               .pathMatchers(HttpMethod.GET, "/public", "/public/**").permitAll()
               .anyExchange().authenticated()
            )
            .oauth2Login(Customizer.withDefaults());

       return http.build();
    }

}
