package com.fercevik.programservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakAdminProperties {
    private String realm;
    private String clientId;
    private String clientSecret;
    private String username;
    private String password;
}
