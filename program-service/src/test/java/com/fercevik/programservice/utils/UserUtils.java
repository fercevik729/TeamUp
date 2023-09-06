package com.fercevik.programservice.utils;

import com.fercevik.programservice.constants.KeycloakConstants;
import com.fercevik.programservice.token.UserInfoDTO;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UserUtils {
    public static UserInfoDTO createMockUser() {
        var principal = new UserInfoDTO();
        var realmRoles = new HashMap<String, List<String>>();
        realmRoles.put("roles", List.of(KeycloakConstants.USER_ROLE));
        principal.setSub(UUID.randomUUID().toString());
        principal.setRealmAccess(realmRoles);
        principal.setScope("email");
        return principal;
    }

    public static UserInfoDTO createMockAdmin() {
        var principal = new UserInfoDTO();
        var realmRoles = new HashMap<String, List<String>>();
        realmRoles.put("roles", List.of(KeycloakConstants.ADMIN_ROLE, KeycloakConstants.USER_ROLE));
        principal.setSub(UUID.randomUUID().toString());
        principal.setRealmAccess(realmRoles);
        principal.setScope("email");
        return principal;
    }
}
