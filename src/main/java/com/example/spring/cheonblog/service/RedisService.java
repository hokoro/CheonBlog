package com.example.spring.cheonblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service

public class RedisService {
    private final RedisTemplate<String , Object> redisTemplate;


    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 저장
    public void saveRefreshToken(String email , String refreshToken){
        redisTemplate.opsForValue().set(email , refreshToken , 7, TimeUnit.DAYS);   // 7일 저장
    }

    // email을 Key refresh Token을 얻는 과정
    public String getRefreshToken(String email){
        return (String) redisTemplate.opsForValue().get(email);
    }

    // 삭제
    public void deleteRefreshToken(String email){
        redisTemplate.delete(email);
    }

}
