package com.sparta.postproject.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String username;
    private String content;
    private String title;
    private int password;
}
