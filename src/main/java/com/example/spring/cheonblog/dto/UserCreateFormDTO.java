package com.example.spring.cheonblog.dto;

import lombok.*;

@Getter
@Setter

public class UserCreateFormDTO {
    private String email;
    private String password;
    private String name;
}
