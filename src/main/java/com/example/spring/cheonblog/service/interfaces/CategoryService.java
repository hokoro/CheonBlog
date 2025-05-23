package com.example.spring.cheonblog.service.interfaces;

import org.springframework.http.ResponseEntity;

import com.example.spring.cheonblog.dto.CategoryCreateFormDTO;
import com.example.spring.cheonblog.dto.CategoryResponseFormDTO;

public interface CategoryService {

    ResponseEntity<CategoryResponseFormDTO> create(CategoryCreateFormDTO categoryCreateFormDTO);



}
