package com.fercevik.authService.service;

import com.fercevik.authService.config.KeycloakProperties;
import com.fercevik.authService.dto.LoginRequest;
import com.fercevik.authService.dto.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@AllArgsConstructor
public class LoginService {
    private final KeycloakProperties properties;
    private final RestTemplate restTemplate;

    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", request.getUsername());
        map.add("password", request.getPassword());
        map.add("client_id", properties.getClientId());
        map.add("grant_type", properties.getGrantType());
        map.add("client_secret", properties.getClientSecret());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(properties.getLoginUrl(),
                httpEntity, LoginResponse.class);

        return ResponseEntity.ok(loginResponse.getBody());

    }
}
