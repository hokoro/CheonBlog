package com.example.spring.cheonblog.service;

// import lombok.RequiredArgsConstructor;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service

public class RedisService {
    private final RedisTemplate<String , Object> redisTemplate;

    // Template 주입
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 저장
    public void saveRefreshToken(String email , String refreshToken){
        redisTemplate.opsForValue().set(email , refreshToken , 7, TimeUnit.DAYS);   // 7일 저장
    }
    // delete
    public void deleteLoginInfo(String email){
        redisTemplate.delete(email);
    }

    // email을 Key refresh Token을 얻는 과정
    public String getRefreshToken(String email){
        Object token = redisTemplate.opsForValue().get(email);

        return token != null ? token.toString():null;
    }

    // 삭제
    public void deleteRefreshToken(String email){
        redisTemplate.delete(email);
    }

    //블랙 리스트 추가
    public void addToBlacklist(String token, long expirationTime) {
        long currentTime = System.currentTimeMillis();  // 시스템 현재 시간
        long ttl = expirationTime - currentTime;    // Time-To-Leave

        if(ttl > 0){
            redisTemplate.opsForValue().set(token,"BLACKLIST",ttl,TimeUnit.MILLISECONDS);    // 만료시간 지나면 자동으로 삭제하게 등록
        }
    }

    // 블랙 리스트 존재 유무
    public boolean isBlackList(String token){
        return redisTemplate.hasKey(token);
    }

}
