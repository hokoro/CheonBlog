package com.example.spring.cheonblog.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY;

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간 (밀리초)

    private final long REFRESH_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7;  // 1주일 refresh Token 지속 시간

    public JwtUtil(@Value("${spring.jwt.key}") String secretKey) {
        SECRET_KEY = secretKey;
    }

    // 토큰 생성
    public String generateAccessToken(String email) {     // 이메일 기반으로 JWT 토큰 생성
        return Jwts.builder()                       // JWT 생성시 Builder를 사용
                .setSubject(email)                  // 내 아이디 = 이메일을 필드로 설정
                .setIssuedAt(new Date())            // 현재시간을 기준으로 토큰 발급 시간을 설정한다.
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // 토큰 만료 시간 1시간
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)    // SHA-256 알고리즘을 사용하여 JWT 서명 추가
                .compact();     // 설정된 정보를 바탕으로 JWT 문자열 형태로 생성
    }

    public String generateRefreshToken(String email) {     // 이메일 기반으로 JWT 토큰 생성
        return Jwts.builder()                       // JWT 생성시 Builder를 사용
                .setSubject(email)                  // 내 아이디 = 이메일을 필드로 설정
                .setIssuedAt(new Date())            // 현재시간을 기준으로 토큰 발급 시간을 설정한다.
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))  // 토큰 만료 시간 1시간
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)    // SHA-256 알고리즘을 사용하여 JWT 서명 추가
                .compact();     // 설정된 정보를 바탕으로 JWT 문자열 형태로 생성
    }

    // 토큰에서 이메일 추출
    public String getEmailFromToken(String token) { // 토큰을 기반으로 이메일 추출
        return Jwts.parser()                        // 이메일을 추출하기 위해 Parser를 호출
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8))) //토큰의 무결성 검증을 위해 SECRET_KEY를 사용하여 무결성을 검증한다.
                .build() // JWT 파서를 생성
                .parseClaimsJws(token)  // 전달된 JWT 토큰을 파싱 ->서명 검증 Jws<Claims> 객체를 반환한다.
                .getBody()              // 파싱된 토큰의 본문을 가져온다. -> 페이로드
                .getSubject();           //필드 값을 반환하며, 여기서는 이메일이 저장
    }

    // 토큰 검증
    public boolean validateToken(String token) {        // 토큰의 유효성을 검증
        try {
            Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8))) // 서명확인
                    .build()            //JWT parser를 생성
                    .parseClaimsJws(token);     // 전달된 JWT 토큰을 파싱하고 서명을 검증한다.
            return true;
        } catch (JwtException | IllegalArgumentException e) {   // 만약 JWT 검증이 실패하면 예외처리
            return false;
        }
    }

    // 만료 시간
    public long getExpirationTime(String token){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8))) // 서명 검증
                    .build()
                    .parseClaimsJws(token)  // JWT 파싱 및 검증
                    .getBody();  // Claims(페이로드) 가져오기
            Date expiration = claims.getExpiration(); // 만료 시간 가져오기
            return expiration.getTime(); // 밀리초 단위로 변환하여 반환

        }catch (JwtException e){
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }


}