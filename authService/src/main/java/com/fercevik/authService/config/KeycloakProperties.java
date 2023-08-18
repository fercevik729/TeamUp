package com.fercevik.authService.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
    private String loginUrl;
    private String grantType;
    private String clientId;
    private String clientSecret;
}
