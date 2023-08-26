package com.fercevik.programservice.services;

import com.fercevik.programservice.config.KeycloakProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// TODO: make this a library for other microservices to use
@Data
@Slf4j
@Service
public class OpaqueTokenService {

    private static final String REALM_ROlES = "realm_access";
    private static final String RESOURCE_ROLES = "resource_access";
    private static final String EMAIL = "email";

    private final KeycloakProperties properties;

    public OpaqueTokenService(KeycloakProperties properties) {
        this.properties = properties;
    }

    /**
     * Extracts all the authorities/scopes from the Bearer token, including Keycloak realm roles
     *
     * @param token Bearer authentication token
     * @return the authorities as a List<GrantedAuthority>
     */
    public List<GrantedAuthority> extractAuthorities(BearerTokenAuthentication token) {
        List<GrantedAuthority> scopes = new ArrayList<>(token.getAuthorities());
        // Extract realm roles as scopes
        Map<String, Object> roles = (Map<String, Object>) token.getTokenAttributes().get(REALM_ROlES);
        if (roles != null) {
            List<String> realm_roles = (List<String>) roles.get("roles");
            // Add realm_roles to scopes
            if (realm_roles != null) {
                var realm_authorities = realm_roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList();
                scopes.addAll(realm_authorities);
            }
        }
        // TODO: extract client roles when needed

        return scopes;
    }

    /**
     * Extracts the email claim from the Bearer token
     *
     * @param token Bearer authentication token
     * @return value associated with the "email" claim or null
     */
    public String extractEmail(BearerTokenAuthentication token) {
        return (String) token.getTokenAttributes().get(EMAIL);
    }

    /**
     * Extracts the user id from the name claim of the Bearer token
     *
     * @param token Bearer authentication token
     * @return user id
     */
    public UUID extractUserId(BearerTokenAuthentication token) {
        return UUID.fromString(token.getName());
    }

    /**
     * Checks if a Bearer token has a particular scope
     *
     * @param token Bearer authentication token
     * @param scope scope name as a String
     * @return true if it has the scope, false otherwise
     */
    public boolean hasAuthority(BearerTokenAuthentication token, String scope) {
        List<GrantedAuthority> scopes = extractAuthorities(token);
        return scopes.stream().anyMatch(r -> r.equals(new SimpleGrantedAuthority(scope)));

    }

}
