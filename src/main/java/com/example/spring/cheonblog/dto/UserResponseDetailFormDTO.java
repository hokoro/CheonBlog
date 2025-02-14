package com.example.spring.cheonblog.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDetailFormDTO {
    private String email;   // 회원 정보의 이메일
    private String name;    // 회원 정보의 이름
}
