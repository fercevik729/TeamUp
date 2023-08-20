package com.fercevik.programservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/programs")
public class ProgramController {

    @GetMapping
    public Mono<String> echo(BearerTokenAuthentication token) {
        log.warn(token.toString());
        return Mono.just("Hello "+token.getName() + " you have the following roles "+token.getAuthorities());
    }
}
