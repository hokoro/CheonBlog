package com.example.spring.cheonblog.service;

import com.example.spring.cheonblog.domain.User;
import com.example.spring.cheonblog.dto.*;
import com.example.spring.cheonblog.jwt.JwtUtil;
import com.example.spring.cheonblog.repository.UserRepository;
import com.example.spring.cheonblog.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;    // User Repository

    private final JwtUtil jwtUtil;

    private final RedisService redisService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  // 비밀번호 조작

    // User Create
    @Override
    public ResponseEntity<UserResponseFormDTO> create(UserCreateFormDTO userCreateFormDTO){
        // Data is Blank
        if(userCreateFormDTO.getEmail().isBlank() || userCreateFormDTO.getPassword().isBlank() || userCreateFormDTO.getName().isBlank()){
            return new ResponseEntity<>(new UserResponseFormDTO("데이터가 존재하지 않습니다.") , HttpStatus.BAD_REQUEST);
        }

        // Success
        Optional<User> member = Optional.ofNullable(userRepository.findByEmail(userCreateFormDTO.getEmail()));  // 이메일 중복된게 있는지 확인

        if(member.isEmpty()){   // 중복되는 아이디가 존재하지 않을 떄 -> 회원가입이 가능할 때
            User user = User.builder().
                    email(userCreateFormDTO.getEmail()).
                    password(passwordEncoder.encode(userCreateFormDTO.getPassword())).
                    name(userCreateFormDTO.getName()).
                    build();
            userRepository.save(user);
            return new ResponseEntity<>(new UserResponseFormDTO("회원가입에 성공하였습니다.") , HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new UserResponseFormDTO("이미 존재하는 아이디입니다.") , HttpStatus.CONFLICT);  // 이미 존재할 때
        }
    }



    @Override
    public ResponseEntity<LoginResponseFormDTO> login(LoginFormDTO loginFormDTO) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(loginFormDTO.getEmail()));

        if (user.isPresent() && passwordEncoder.matches(loginFormDTO.getPassword(), user.get().getPassword())) {    // 로그인 정보가 일치 했을 때
            String accessToken = jwtUtil.generateAccessToken(loginFormDTO.getEmail());              // JwtUtil을 사용해 jwt 토큰 생성
            String refreshToken = jwtUtil.generateRefreshToken(loginFormDTO.getEmail());              // JwtUtil을 사용해 jwt 토큰 생성

            redisService.saveRefreshToken(loginFormDTO.getEmail() , passwordEncoder.encode(refreshToken));

            return new ResponseEntity<>(new LoginResponseFormDTO(accessToken ,refreshToken, "로그인 성공했습니다."),HttpStatus.OK);
        }
        return new ResponseEntity<>(new LoginResponseFormDTO(null,null,"이메일이나 비밀번호가 일치하지 않습니다."), HttpStatus.UNAUTHORIZED);
    }



    // 토큰 재발급
    @Override
    public ResponseEntity<RefreshResponseFormDTO> refresh(RefreshFormDTO refreshFormDTO){
        String refreshToken = refreshFormDTO.getRefreshToken();

        if(refreshToken!=null && jwtUtil.validateToken(refreshToken)){
            String email = jwtUtil.getEmailFromToken(refreshToken);
            if(passwordEncoder.matches(refreshToken, redisService.getRefreshToken(email))){
                String newAccessToken = jwtUtil.generateAccessToken(email);
                return new ResponseEntity<>(new RefreshResponseFormDTO(newAccessToken , "새로운 토큰을 발급합니다."), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(new RefreshResponseFormDTO("null","사용할 수 없는 토큰입니다."),HttpStatus.UNAUTHORIZED);

    }



    //로그 아웃
    @Override
    public ResponseEntity<LogoutResponseFormDTO> logout(LogoutFormDTO logoutFormDTO) {
        String accessToken = logoutFormDTO.getAccessToken();

        if (accessToken.startsWith("Bearer ")) {  // Bearer 문자 제거
            accessToken = accessToken.substring(7);
        }

        if (jwtUtil.validateToken(accessToken)) {       // 검증된 사용자인지
            String email = jwtUtil.getEmailFromToken(accessToken);   // 이메일 추출
            long expiration = jwtUtil.getExpirationTime(accessToken);   // 시간 추출
            redisService.addToBlacklist(accessToken, expiration);  // 사용자 블랙리스트 등록
            return new ResponseEntity<>(new LogoutResponseFormDTO("로그아웃 되었습니다."),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new LogoutResponseFormDTO("유효한 토큰이 아닙니다."), HttpStatus.UNAUTHORIZED);
        }
    }


}
