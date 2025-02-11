package com.example.spring.cheonblog.service.interfaces;

import com.example.spring.cheonblog.dto.EmailValidFormDTO;
import com.example.spring.cheonblog.dto.EmailValidResponseFormDTO;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

public interface EmailService {

    ResponseEntity<EmailValidResponseFormDTO> valid(EmailValidFormDTO emailValidFormDTO) throws MessagingException;
}
