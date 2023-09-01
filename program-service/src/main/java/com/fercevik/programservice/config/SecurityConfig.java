package com.fercevik.programservice.config;

import com.fercevik.programservice.constants.KeycloakConstants;
import com.fercevik.programservice.token.KeycloakAuthoritiesOpaqueTokenIntrospector;
import com.fercevik.programservice.token.IntrospectionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final IntrospectionService service;

    public SecurityConfig(IntrospectionService service) {
        this.service = service;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/programs", "/programs/**").hasAuthority(KeycloakConstants.USER_ROLE)
                .requestMatchers("/programs/echo").hasAnyAuthority(KeycloakConstants.USER_ROLE)
                .anyRequest().authenticated());
        http.oauth2ResourceServer(oauth2 -> oauth2.opaqueToken(opaqueToken -> opaqueToken.introspector(myIntrospector())));


        return http.build();
    }

    @Bean
    public OpaqueTokenIntrospector myIntrospector() {
        return new KeycloakAuthoritiesOpaqueTokenIntrospector(service);
    }
}
