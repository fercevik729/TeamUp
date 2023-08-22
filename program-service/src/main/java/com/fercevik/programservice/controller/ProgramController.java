package com.fercevik.programservice.controller;

import com.fercevik.programservice.constants.KeycloakConstants;
import com.fercevik.programservice.services.OpaqueTokenService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@Slf4j
@RequestMapping("/programs")
public class ProgramController {
    private final OpaqueTokenService opaqueTokenService;

    @GetMapping
    public ResponseEntity<String> echo(BearerTokenAuthentication token) {
        log.warn(token.getName());
        if (opaqueTokenService.hasAuthority(token, KeycloakConstants.USER_ROLE)) {
            return ResponseEntity.ok("Hello Sir "+opaqueTokenService.extractEmail(token) + ", you have reached a secure endpoint");
        } else if (opaqueTokenService.hasAuthority(token, KeycloakConstants.ADMIN_ROLE)) {
            return ResponseEntity.ok("HELLO MR. ADMIN");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
