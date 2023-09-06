package com.fercevik.programservice.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class UserInfoDTO implements OAuth2AuthenticatedPrincipal {
    @JsonProperty("sub")
    private String sub;

    @JsonProperty("typ")
    private String typ;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("realm_access")
    private Map<String, List<String>> realmAccess;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("active")
    private Boolean active;

    @Override
    public<A> A getAttribute(String name) {
        return (A) getAttributes().get(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("sub", getSub());
        attrs.put("type", getTyp());
        attrs.put("name", getName());
        attrs.put("email", getEmail());
        attrs.put("realm_access", getRealmAccess());
        attrs.put("resource_access", getRealmAccess());
        attrs.put("scope", getScope());
        attrs.put("username", getUsername());
        attrs.put("active", getActive());
        return attrs;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> scopes = new java.util.ArrayList<>(List.of(getScope().split(" ")));
        // Get Keycloak roles
        scopes.addAll(getRealmAccess().get("roles"));

        return scopes.stream().map(SimpleGrantedAuthority::new).toList();
    }
}