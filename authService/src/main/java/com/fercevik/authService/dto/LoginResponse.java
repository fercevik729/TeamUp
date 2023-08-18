package com.fercevik.authService.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String access_token;
    private String refresh_token;
    private int expires_in;
    private int refresh_expires_in;
    private String token_type;

}
