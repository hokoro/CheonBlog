package com.example.spring.cheonblog.controller;

import com.example.spring.cheonblog.dto.LoginFormDTO;
import com.example.spring.cheonblog.dto.LoginResponseFormDTO;
import com.example.spring.cheonblog.dto.UserCreateFormDTO;
import com.example.spring.cheonblog.dto.UserResponseFormDTO;
import com.example.spring.cheonblog.service.interfaces.UserService;
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
    public ResponseEntity<LoginResponseFormDTO> login(@RequestBody LoginFormDTO loginFormDTO) {
        return userService.login(loginFormDTO);
    }

}