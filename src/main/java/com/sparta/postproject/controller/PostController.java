package com.sparta.postproject.controller;

import com.sparta.postproject.dto.PostRequestDto;
import com.sparta.postproject.dto.PostResponseDto;
import com.sparta.postproject.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(){
        return postService.findAll();
    }

    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto)
    {
        return postService.createPost(postRequestDto);

    }
/*
    @GetMapping("/post/{id}")
    public PostResponseDto getPost(@PathVariable("id") Long id ){

    }

    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable("id") Long id ){

    }

    @DeleteMapping("/post/{id}")
    public PostResponseDto deletePost(@PathVariable("id") Long id ){

    }
    */

}
