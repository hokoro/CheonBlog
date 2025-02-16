package com.example.spring.cheonblog.controller;

import com.example.spring.cheonblog.dto.*;
import com.example.spring.cheonblog.service.interfaces.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserAPIController {
    private final UserService userService;


    @PostMapping("/user/create")
    public ResponseEntity<UserResponseFormDTO> create(@RequestBody UserCreateFormDTO userCreateFormDTO){
        return userService.create(userCreateFormDTO);
    }

    @PostMapping("/user/login")
    public ResponseEntity<LoginResponseFormDTO> login(@RequestBody LoginFormDTO loginFormDTO , HttpServletResponse response) {
        return userService.login(loginFormDTO , response);
    }

    @PostMapping("/user/redis")
    public ResponseEntity<RefreshResponseFormDTO> refresh(@RequestBody RefreshFormDTO refreshFormDTO){
        return userService.refresh(refreshFormDTO);
    }

    @PostMapping("/user/logout")
    public ResponseEntity<LogoutResponseFormDTO> logout(@RequestBody LogoutFormDTO logoutFormDTO){
        return userService.logout(logoutFormDTO);
    }

    @PostMapping("/user/detail")
    public ResponseEntity<UserResponseDetailFormDTO> detail(@RequestBody UserDetailFormDTO userDetailFormDTO){
        return userService.detail(userDetailFormDTO);
    }

    @PostMapping("/user/delete")
    public ResponseEntity<UserResponseFormDTO> delete(@RequestBody UserDeleteFormDTO userDeleteFormDTO){
        return userService.delete(userDeleteFormDTO);
    }

}