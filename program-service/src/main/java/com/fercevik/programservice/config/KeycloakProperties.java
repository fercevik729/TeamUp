package com.fercevik.programservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
    private String host = "unset";
    private String realm = "unset";
    private String clientId = "unset";
    private String clientSecret = "unset";
    private String username = "unset";
    private String password = "unset";
    private String introspectionUri = "unset";
}
