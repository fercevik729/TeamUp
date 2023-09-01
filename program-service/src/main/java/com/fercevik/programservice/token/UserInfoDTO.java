package com.fercevik.programservice.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserInfoDTO {
    @JsonProperty("sub")
    private String sub;

    @JsonProperty("typ")
    private String typ;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("realm_access")
    private RealmAccess realmAccess;

    @JsonProperty("resource_access")
    private ResourceAccess resourceAccess;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("active")
    private Boolean active;

    // Getter and setter methods for fields
}

@Data
 class RealmAccess {
    @JsonProperty("roles")
    private String[] roles;

    // Getter and setter methods for roles
}

@Data
class ResourceAccess {
    @JsonProperty("team-up-client")
    private ResourceRoles teamUpClient;

    @JsonProperty("account")
    private ResourceRoles account;
}

@Data
class ResourceRoles {
    private String[] roles;
}
