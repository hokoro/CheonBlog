package com.example.spring.cheonblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.cheonblog.domain.Post;

public interface PostRepository extends JpaRepository<Post , Long> {

    
} 