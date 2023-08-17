package com.fercevik.authService.dto;

import lombok.Data;

@Data
public class GoogleOAuth2UserInfo {
    private String email;
    private String id;
    private String imageUrl;
    private String name;
}
