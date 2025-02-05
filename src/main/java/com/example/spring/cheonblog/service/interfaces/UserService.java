package com.example.spring.cheonblog.service.interfaces;

import com.example.spring.cheonblog.dto.LoginFormDTO;
import com.example.spring.cheonblog.dto.LoginResponseFormDTO;
import com.example.spring.cheonblog.dto.UserCreateFormDTO;
import com.example.spring.cheonblog.dto.UserResponseFormDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    // 계정 생성
    ResponseEntity<UserResponseFormDTO> create(UserCreateFormDTO userCreateFormDTO);

    ResponseEntity<LoginResponseFormDTO> login(LoginFormDTO loginFormDTO);
}
