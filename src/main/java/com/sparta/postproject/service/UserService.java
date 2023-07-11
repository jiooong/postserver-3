package com.sparta.postproject.service;


import com.sparta.postproject.dto.LoginRequestDto;
import com.sparta.postproject.dto.SignupRequestDto;
import com.sparta.postproject.dto.StatusCodeResponseDto;
import com.sparta.postproject.entity.User;
import com.sparta.postproject.entity.UserRoleEnum;
import com.sparta.postproject.repository.UserRepository;
import com.sparta.postproject.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public StatusCodeResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }


        // 사용자 등록
        User user = new User(username, password, role);
        userRepository.save(user);

        // return
        return  new StatusCodeResponseDto(HttpStatus.CREATED.value(), "회원가입 성공");
    }

    public StatusCodeResponseDto login(LoginRequestDto requestDto, HttpServletResponse jwtResponse) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("등록된 사용자가 없습니다."));
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }


        // Jwt 토큰 생성, response에 넣기
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        // Jwt 쿠키 저장
        jwtUtil.addJwtToCookie(token, jwtResponse);

        StatusCodeResponseDto responseDto = new StatusCodeResponseDto(HttpStatus.OK.value(), "로그인 성공");

        return responseDto;
    }
}
