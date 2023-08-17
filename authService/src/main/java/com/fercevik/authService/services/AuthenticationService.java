package com.fercevik.authService.services;

import com.fercevik.authService.auth.RegisterRequest;
import com.fercevik.authService.model.Provider;
import com.fercevik.authService.model.User;
import com.fercevik.authService.dao.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    public User register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getFirstname() + request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .provider(Provider.LOCAL)
                .build();
        return repository.save(user);
    }

    public void deleteUser(String email) {
        repository.deleteUserByEmail(email);
    }
}
