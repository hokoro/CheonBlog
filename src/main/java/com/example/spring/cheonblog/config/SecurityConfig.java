package com.example.spring.cheonblog.config;


import com.example.spring.cheonblog.jwt.JwtAuthenticationFilter;            // JWT 인증 필터
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor        // 의존성 주입 자동생성 (final)
public class SecurityConfig{

    private final JwtAuthenticationFilter jwtAuthenticationFilter;      // JWT 인증 필터 주입

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{ // 보안 설정
        http
                .csrf(AbstractHttpConfigurer::disable)              // CSRF 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))   // 세션을 사용하지 않고, STATELESS 무상태 방식으로 연결
                .authorizeHttpRequests(authorize ->authorize                        //접근 권한 설정
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);       // 이후에 JWT 인증 필터를 추가
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){   // 비밀번호 암호화 기능
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {  // Spring Security 인증을 담당하는 AuthenticationManager 생성
        return authenticationConfiguration.getAuthenticationManager();
    }

}
