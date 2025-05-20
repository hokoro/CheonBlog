package com.example.spring.cheonblog.dto;


import com.example.spring.cheonblog.domain.PostStatus;

import lombok.*;

@Getter 
@Setter 

public class PostCreateFormDTO {
    private String title;
    private String content;
    private Long categoryId;    // 클라이언트가 Category 자체를 넘기는건 위험이 있다.
    private PostStatus status;

}
