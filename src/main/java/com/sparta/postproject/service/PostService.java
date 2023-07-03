package com.sparta.postproject.service;

import com.sparta.postproject.dto.PostRequestDto;
import com.sparta.postproject.dto.PostResponseDto;
import com.sparta.postproject.entity.Post;
import com.sparta.postproject.repository.PostRepository;

import java.util.List;

public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostResponseDto> findAll() {

        return postRepository.findAll().stream().map(PostResponseDto::new).toList();
    }

    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        Post addpost = postRepository.save(post);
        return new PostResponseDto(addpost);

    }
}
