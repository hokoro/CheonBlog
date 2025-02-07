package com.example.spring.cheonblog.service.interfaces;

import com.example.spring.cheonblog.dto.*;
import org.springframework.http.ResponseEntity;

public interface UserService {
    // 계정 생성
    ResponseEntity<UserResponseFormDTO> create(UserCreateFormDTO userCreateFormDTO);

    ResponseEntity<LoginResponseFormDTO> login(LoginFormDTO loginFormDTO);

    ResponseEntity<RefreshResponseFormDTO> refresh(RefreshFormDTO refreshFormDTO);


    ResponseEntity<LogoutResponseFormDTO> logout(LogoutFormDTO logoutFormDTO);
}
