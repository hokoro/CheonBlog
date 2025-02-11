package com.example.spring.cheonblog.service;

import com.example.spring.cheonblog.dto.EmailValidFormDTO;
import com.example.spring.cheonblog.dto.EmailValidResponseFormDTO;
import com.example.spring.cheonblog.service.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;


    public String generateVerificationCode(){
        int code = 100000 + new SecureRandom().nextInt(900000);
        return String.valueOf(code);
    }

    @Override
    public ResponseEntity<EmailValidResponseFormDTO> valid(EmailValidFormDTO emailValidFormDTO) throws MessagingException {
        if(emailValidFormDTO.getEmail() == null || emailValidFormDTO.getEmail().isBlank()){
            return new ResponseEntity<>(new EmailValidResponseFormDTO("보내는 사람의 정보가 없습니다"), HttpStatus.BAD_REQUEST);
        }

        System.out.println(emailValidFormDTO.getEmail());

        String verificationCode = generateVerificationCode();   // 인증번호 발급

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

        helper.setTo(emailValidFormDTO.getEmail());
        helper.setSubject("이메일 인증코드");
        helper.setText("인증번호: " + verificationCode);

        javaMailSender.send(message);
        return new ResponseEntity<>(new EmailValidResponseFormDTO("인증번호를 전송 했습니다.") , HttpStatus.OK);
    }
}
