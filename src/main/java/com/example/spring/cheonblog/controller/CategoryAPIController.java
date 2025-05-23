package com.example.spring.cheonblog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.cheonblog.dto.CategoryCreateFormDTO;
import com.example.spring.cheonblog.dto.CategoryResponseFormDTO;
import com.example.spring.cheonblog.service.interfaces.CategoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryAPIController {
    
    private final CategoryService categoryService;

    @PostMapping("/category/create")
    public ResponseEntity <CategoryResponseFormDTO> create(@RequestBody CategoryCreateFormDTO categoryCreateFormDTO){
        return categoryService.create(categoryCreateFormDTO);
    }
    
    
}
