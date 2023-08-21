package com.fercevik.programservice.controller;

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
    private static final String USER_ROLE = "ROLE_app_user";

    @GetMapping
    public ResponseEntity<String> echo(BearerTokenAuthentication token) {
        log.warn(token.toString());
        if (opaqueTokenService.hasAuthority(token, USER_ROLE)) {
            return ResponseEntity.ok().body("Hello Sir "+token.getName() + ", you have reached a secure endpoint");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
