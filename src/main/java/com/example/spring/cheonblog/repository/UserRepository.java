package com.example.spring.cheonblog.repository;

import com.example.spring.cheonblog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);  // 중복되는 아이디 찾기

    User findByNameAndEmail(String name , String email);    // 정보 조회 
}
