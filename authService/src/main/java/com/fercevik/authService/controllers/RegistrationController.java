package com.fercevik.authService.controllers;

import com.fercevik.authService.exceptions.UserAlreadyExistsException;
import com.fercevik.authService.auth.RegisterRequest;
import com.fercevik.authService.model.Provider;
import com.fercevik.authService.model.User;
import com.fercevik.authService.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid RegisterRequest request) {
        try {
            userService.loadUserByUsername(request.getEmail());
            throw new UserAlreadyExistsException("email: "+request.getEmail());
        } catch (UsernameNotFoundException e) {
            User newUser = User.builder()
                    .name(request.getFirstname() + request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .provider(Provider.LOCAL)
                    .build();
            userService.save(newUser);
            return ResponseEntity.ok("You have been successfully registered");
        }
    }

}
