package com.example.spring.cheonblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailValidResponseFormDTO {
    private String message;
    private String number;
}
