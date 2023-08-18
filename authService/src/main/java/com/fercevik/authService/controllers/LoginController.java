package com.fercevik.authService.controllers;

import com.fercevik.authService.dto.LoginRequest;
import com.fercevik.authService.dto.LoginResponse;
import com.fercevik.authService.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class LoginController {
    private LoginService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        log.info("Executing Keycloak Login");
        return service.login(request);
    }
}
