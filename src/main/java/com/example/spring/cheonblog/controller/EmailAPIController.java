package com.example.spring.cheonblog.controller;


import com.example.spring.cheonblog.dto.EmailValidFormDTO;
import com.example.spring.cheonblog.dto.EmailValidResponseFormDTO;
import com.example.spring.cheonblog.service.interfaces.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmailAPIController {

    private final EmailService emailService;


    @PostMapping("/email/valid")
    public ResponseEntity<EmailValidResponseFormDTO> valid(@RequestBody EmailValidFormDTO emailValidFormDTO) throws MessagingException {
        return emailService.valid(emailValidFormDTO);
    }
}
