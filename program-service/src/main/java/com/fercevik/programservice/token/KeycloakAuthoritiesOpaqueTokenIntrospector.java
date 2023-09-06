package com.fercevik.programservice.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.util.Collection;

@Slf4j
public class KeycloakAuthoritiesOpaqueTokenIntrospector implements OpaqueTokenIntrospector {
    private final IntrospectionService delegate;

    public KeycloakAuthoritiesOpaqueTokenIntrospector(IntrospectionService delegate) {
        this.delegate = delegate;
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        var userInfoDTO = this.delegate.introspect(token);
        return new DefaultOAuth2AuthenticatedPrincipal(
                userInfoDTO.getName(), userInfoDTO.getAttributes(),
                (Collection<GrantedAuthority>) userInfoDTO.getAuthorities());
    }
}
