package com.fercevik.programservice.services;

import com.fercevik.programservice.config.KeycloakProperties;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

// TODO: make this a library for other microservices to use
@Data
@Service
public class OpaqueTokenService {

    private static final String REALM_ROlES = "realm_access";
    private static final String RESOURCE_ROLES = "resource_access";

    private final KeycloakProperties properties;

    public OpaqueTokenService(KeycloakProperties properties) {
        this.properties = properties;
    }

    public Collection<GrantedAuthority> extractAuthorities(BearerTokenAuthentication token) {
        Collection<GrantedAuthority> scopes = token.getAuthorities();
        // Extract realm roles as scopes
        Map<String, Object> roles = (Map<String, Object>) token.getTokenAttributes().get(REALM_ROlES);
        if (roles != null) {
            List<String> realm_roles = (List<String>) roles.get("roles");
            // Add realm_roles to scopes
            if (realm_roles != null) {
                scopes.addAll(realm_roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList());
            }
        }
        // TODO: extract client roles when needed

        return scopes;

    }

}
