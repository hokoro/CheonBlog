package com.example.spring.cheonblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){     // CORS 설정
        registry.addMapping("/**")          // CORS가 적용될 URL 패턴 -> 모든 URL 패턴에서 CORS 정책을 적용
                .allowedOrigins("https://localhost:3000")    // 3000번째 오는 요청을 허용한다.
                .allowedMethods("POST","GET","PUT","DELETE","OPTIONS")  // 허용하는 HTTP 메서드 요청
                .allowedHeaders("*")    // 허용하는 헤더
                .allowCredentials(true);    // 클라이언트에서 자격증명을 포함한 요청을 보낼 수 있도록 허용

    }

}