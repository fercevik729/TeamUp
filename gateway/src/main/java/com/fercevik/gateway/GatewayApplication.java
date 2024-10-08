package com.fercevik.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

import java.net.URI;

@SpringBootApplication
@EnableWebFluxSecurity
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public ServerLogoutSuccessHandler keycloakLogoutSuccessHandler(ReactiveClientRegistrationRepository repository) {
        var oidcLogoutSuccessHandler = new OidcClientInitiatedServerLogoutSuccessHandler(repository);
        oidcLogoutSuccessHandler.setLogoutSuccessUrl(
                URI.create("http://localhost:8080/realms/TeamUp/protocol/openid-connect/logout"));
        return oidcLogoutSuccessHandler;
    }

    @Bean
    public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity http,
                                                               ServerLogoutSuccessHandler handler) {
        http.authorizeExchange(
                        auth -> auth.pathMatchers("/actuator/**", "/", "/index.html", "/features.html").permitAll()
                                .anyExchange().authenticated()).oauth2Login(Customizer.withDefaults())
                .logout(logout -> logout.logoutSuccessHandler(handler));
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }

}
