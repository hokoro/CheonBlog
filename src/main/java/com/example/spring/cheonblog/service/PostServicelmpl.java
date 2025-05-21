package com.example.spring.cheonblog.service;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.example.spring.cheonblog.domain.Category;
import com.example.spring.cheonblog.service.interfaces.PostService;
import com.example.spring.cheonblog.repository.*;
import com.example.spring.cheonblog.domain.Post;
import com.example.spring.cheonblog.domain.User;
import com.example.spring.cheonblog.dto.PostCreateFormDTO;
import com.example.spring.cheonblog.dto.PostResponseFormDTO;
import com.example.spring.cheonblog.jwt.JwtUtil;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServicelmpl implements PostService {

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;


    private final PostRepository postRepository;

    private final JwtUtil jwtUtil;

    private final RedisService redisService;

    // create 
    @Override
    public ResponseEntity<PostResponseFormDTO> create(PostCreateFormDTO postCreateFormDTO , String token){
        String accessToken = token;

        accessToken = accessToken.trim();   // 앞뒤 공백 제거 
        
        // jwt에 접근할 토큰 전처리 
        if(accessToken.startsWith("Bearer ")){
            accessToken = accessToken.substring(7);
        }


        if(jwtUtil.validateToken(accessToken, redisService)){
            // DB에 존재하는 사용자인지 
            String userEmail = jwtUtil.getEmailFromToken(accessToken);

            User user = userRepository.findByEmail(userEmail);

            if(user == null){
                return new ResponseEntity<>(new PostResponseFormDTO("등록된 사용자가 아닙니다.") , HttpStatus.NOT_FOUND);
            }
            
            if(postCreateFormDTO.getTitle().isBlank() || postCreateFormDTO.getContent().isBlank()){
                return new ResponseEntity<>(new PostResponseFormDTO("비어있는 내용이 있습니다") , HttpStatus.BAD_REQUEST);
            }

            if(!categoryRepository.existsById(postCreateFormDTO.getCategoryId())){
                return new ResponseEntity<>(new PostResponseFormDTO("존재하지 않는 카테고리입니다") , HttpStatus.NOT_FOUND);
            }
            
           
            Category category = categoryRepository.findById(postCreateFormDTO.getCategoryId()).get();



            Post post = Post.builder().
                        title(postCreateFormDTO.getTitle()).
                        content(postCreateFormDTO.getContent()).
                        author(user).
                        category(category).
                        status(postCreateFormDTO.getStatus()).
                        isDeleted(false).
                        viewCount(0L).
                        createdAt(LocalDateTime.now()).
                        updateAt(LocalDateTime.now()).
                        build();
            postRepository.save(post);
            return new ResponseEntity<>(new PostResponseFormDTO("게시글이 등록되었습니다."), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new PostResponseFormDTO("유효한 토큰이 아닙니다.") , HttpStatus.UNAUTHORIZED);
        }

    }
}
