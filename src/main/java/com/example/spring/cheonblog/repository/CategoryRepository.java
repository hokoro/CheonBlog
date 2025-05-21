package com.example.spring.cheonblog.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.cheonblog.domain.Category;

public interface CategoryRepository extends JpaRepository<Category , Long> {
    
}
