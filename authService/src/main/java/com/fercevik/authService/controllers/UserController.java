package com.fercevik.authService.controllers;

import com.fercevik.authService.exceptions.UserAlreadyExistsException;
import com.fercevik.authService.auth.RegisterRequest;
import com.fercevik.authService.model.Provider;
import com.fercevik.authService.model.User;
import com.fercevik.authService.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUserAccount(@RequestBody RegisterRequest request) {

        try {
            userService.loadUserByUsername(request.getEmail());
            throw new UserAlreadyExistsException("email: "+request.getEmail());
        } catch (UsernameNotFoundException e) {
            User newUser = User.builder()
                    .name(request.getFirstName() + request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .provider(Provider.LOCAL)
                    .build();
            userService.save(newUser);
            return ResponseEntity.ok("You have been successfully registered");
        }
    }

    @PostMapping("/delete-account")
    public ResponseEntity<String> deleteUserAccount(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.warn("In /delete-account got user: "+user.toString());
        userService.deleteAccount(user);

        return ResponseEntity.ok("Your account has been deleted.");
    }

}
