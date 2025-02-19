package com.example.spring.cheonblog.dto;


import lombok.*;

@Getter
@Setter
public class UserUpdateFormDTO {
    private String name;    // 이름 변경
    private String password;    // 비밀번호 변경
    private String accessToken;   // 접근 가능하진 여부를 파악하는 토큰
}
