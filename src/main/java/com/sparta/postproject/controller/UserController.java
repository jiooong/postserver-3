package com.sparta.postproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class UserController {

    @PostMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/signup")
    public String signupPage(){
        return "signup";
    }
}
