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
        if(emailValidFormDTO.getEmail() == null || emailValidFormDTO.getEmail().isBlank()){     // 받는 사람 정보가 없을 경우
            return new ResponseEntity<>(new EmailValidResponseFormDTO("보내는 사람의 정보가 없습니다" , null), HttpStatus.BAD_REQUEST);
        }


        String verificationCode = generateVerificationCode();   // 인증번호 발급

        MimeMessage message = javaMailSender.createMimeMessage();           // 메서지 객체 생성
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

        // 메시지 정보 입력
        helper.setTo(emailValidFormDTO.getEmail());
        helper.setSubject("이메일 인증코드");
        helper.setText("인증번호: " + verificationCode);

        // 메시지 보내기
        javaMailSender.send(message);
        return new ResponseEntity<>(new EmailValidResponseFormDTO("인증번호를 전송 했습니다." , verificationCode) , HttpStatus.OK);
    }
}
