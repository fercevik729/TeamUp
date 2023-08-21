package com.fercevik.programservice.controller;

import com.fercevik.programservice.services.OpaqueTokenService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@Data
@Slf4j
@RequestMapping("/programs")
public class ProgramController {

    private final OpaqueTokenService opaqueTokenService;

    @GetMapping
    public Mono<String> echo(BearerTokenAuthentication token) {
        log.warn(token.toString());
        Collection<GrantedAuthority> roles = opaqueTokenService.extractAuthorities(token);
        return Mono.just("Hello Sir "+token.getName() + ", you have the following roles "+roles);
    }
}
