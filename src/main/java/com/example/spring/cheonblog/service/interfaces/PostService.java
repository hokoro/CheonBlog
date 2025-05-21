package com.example.spring.cheonblog.service.interfaces;

import org.springframework.http.ResponseEntity;

import com.example.spring.cheonblog.dto.PostCreateFormDTO;
import com.example.spring.cheonblog.dto.PostResponseFormDTO;

public interface PostService {
    ResponseEntity<PostResponseFormDTO> create(PostCreateFormDTO postCreateFormDTO , String token);   
}
