package com.example.spring.cheonblog.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor    // 생성자 주입을 자동으로 생성
public class JwtAuthenticationFilter extends OncePerRequestFilter {     // 요청마다 한 번 실행됨

    private final JwtUtil jwtUtil;      // JWT 유틸리티 클래스 의존성 주입

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)          // JWT 검증하고 검증된 사용자 정보를 Security Context Holder에 저장  -> 인증된 사용자를 저장하는 저장소 역할
            throws ServletException, IOException {

        String token = getTokenFromRequest(request);            //HTTP 요청에서 JWT 토큰을 추출
        if (token != null && jwtUtil.validateToken(token)) {    // 토큰이 존재하고 검증도 완벽한 경우
            String email = jwtUtil.getEmailFromToken(token);     // 이메일 추출
            UserDetails userDetails = User.builder()
                    .username(email)                            // 사용자 설정 이메일
                    .password("")                               // 비밀번호
                    .roles("USER")                              // 기본적으로 USER 역할 부여
                    .build();                                   // 생성

            UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities()); // 인증 객체 생성

            SecurityContextHolder.getContext().setAuthentication(authentication);   //보안 컨텍스트 인증 정보 저장
        }

        filterChain.doFilter(request, response);            // 다음 필터로 요청 전달
    }

    private String getTokenFromRequest(HttpServletRequest request) {            // JWT 토큰 추출 역할
        String bearerToken = request.getHeader("Authorization");            // HTTP Header에서 Authorization 값 가져오기
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {         // Bearer로 시작하는지 확인
            return bearerToken.substring(7);                        // 이후 7자리 값만 반환
        }
        return null;        // 토큰이 없거나 형식에 맞지 않다면 null 반환
    }
}