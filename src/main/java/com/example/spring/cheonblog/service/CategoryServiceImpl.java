package com.example.spring.cheonblog.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.spring.cheonblog.domain.User;
import com.example.spring.cheonblog.domain.Category;
import com.example.spring.cheonblog.dto.CategoryCreateFormDTO;
import com.example.spring.cheonblog.dto.CategoryResponseFormDTO;
import com.example.spring.cheonblog.jwt.JwtUtil;
import com.example.spring.cheonblog.repository.CategoryRepository;
import com.example.spring.cheonblog.repository.UserRepository;
import com.example.spring.cheonblog.service.interfaces.CategoryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service 
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final JwtUtil jwtUtil;

    private final RedisService redisService;

    @Override
    public ResponseEntity<CategoryResponseFormDTO> create(CategoryCreateFormDTO categoryCreateFormDTO){
        


        // data is blank
        if(categoryCreateFormDTO.getName().isBlank()){
            return new ResponseEntity<>(new CategoryResponseFormDTO("카테고리가 비어있습니다.") , HttpStatus.BAD_REQUEST);
        }

        if(categoryCreateFormDTO.getAccessToken().isBlank()){
            return new ResponseEntity<>(new CategoryResponseFormDTO("인증 토큰이 존재하지 않습니다.") , HttpStatus.BAD_REQUEST);
        }

        // 로그인 인증 
        String accessToken = categoryCreateFormDTO.getAccessToken();

        accessToken = accessToken.trim();

        if(accessToken.startsWith("Bearer ")){
            accessToken = accessToken.substring(7);
        }

        if(jwtUtil.validateToken(accessToken, redisService)){
            String userEmail = jwtUtil.getEmailFromToken(accessToken);

            User user = userRepository.findByEmail(userEmail);

            if(user == null){
                return new ResponseEntity<>(new CategoryResponseFormDTO("등록된 사용자가 아닙니다.") , HttpStatus.NOT_FOUND);
            }

            // 카테고리가 이미 존재하는지 아니면 새로운 카테고리인지 

            Category category = categoryRepository.findByName(categoryCreateFormDTO.getName());

            // 새로운 카테고리 인 경우 
            if(category == null){
                Category categoryInfo = Category.builder().
                                        name(categoryCreateFormDTO.getName()).
                                        description(categoryCreateFormDTO.getDescription()).build();

                categoryRepository.save(categoryInfo);
                return new ResponseEntity<>(new CategoryResponseFormDTO("카테고리 등록") , HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new CategoryResponseFormDTO("이미 존재하는 카테고리입니다.") , HttpStatus.CONFLICT);
            }

            
        }else{
            return new ResponseEntity<>(new CategoryResponseFormDTO("인증된 사용자가 아닙니다") , HttpStatus.UNAUTHORIZED);
        }



    }
    
}
