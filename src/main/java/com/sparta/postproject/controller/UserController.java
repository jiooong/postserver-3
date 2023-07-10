package com.sparta.postproject.controller;

import com.sparta.postproject.dto.LoginRequestDto;
import com.sparta.postproject.dto.SignupRequestDto;
import com.sparta.postproject.dto.StatusCodeResponseDto;
import com.sparta.postproject.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Slf4j Logger 객체 생성 없이 바로 log.debug()로 로그를 찍어볼 수 있다
@Slf4j
@RestController // Controller + Responsebody
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public StatusCodeResponseDto signup(@RequestBody @Valid SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @PostMapping("/login")
    public StatusCodeResponseDto login(@RequestBody LoginRequestDto requestDto, HttpServletResponse JwtResponse) {
        return userService.login(requestDto, JwtResponse);
    }
}