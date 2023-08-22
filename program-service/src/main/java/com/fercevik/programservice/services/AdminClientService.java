package com.fercevik.programservice.services;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Data
public class AdminClientService {

    @Value("${keycloak.realm}")
    private static String REALM_NAME;

    private final Keycloak keycloak;

    void searchByUsername(String username, boolean exact) {
        log.info("Searching by username: {} (exact {})", username, exact);
        List<UserRepresentation> users = keycloak.realm(REALM_NAME)
                .users()
                .searchByUsername(username, exact);

        log.info("Users found by username {}", users.stream()
                .map(UserRepresentation::getUsername)
                .collect(Collectors.toList()));
    }

}
