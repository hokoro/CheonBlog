package com.example.spring.cheonblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseFormDTO {
    private String accessToken;
    private String refreshToken;
    private String message;
}
