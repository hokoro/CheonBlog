package com.example.spring.cheonblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RefreshResponseFormDTO {
    private String accessToken; // 다시 재발급할 토큰
    private String message; // 메시지
}
