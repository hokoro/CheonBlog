package com.example.spring.cheonblog.dto;

import lombok.*;


@Getter
@Setter
public class CategoryCreateFormDTO {
    private String name;
    private String description;
    private String accessToken;
}
