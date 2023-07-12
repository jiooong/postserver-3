package com.sparta.postproject.service;

import com.sparta.postproject.dto.PostRequestDto;
import com.sparta.postproject.dto.PostResponseDto;
import com.sparta.postproject.entity.Post;
import com.sparta.postproject.entity.User;
import com.sparta.postproject.repository.PostRepository;
import com.sparta.postproject.repository.UserRepository;
import com.sparta.postproject.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    public PostService(PostRepository postRepository, JwtUtil jwtUtil, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<PostResponseDto> findAll() {

        //return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream().map(PostResponseDto::new).toList();
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();

    }

    public PostResponseDto createPost(PostRequestDto postRequestDto, String token) {
        String username = getUsername(token);
        Post post = new Post(postRequestDto, username);
        User user = userRepository.findByUsername(username).orElseThrow(()->
                new IllegalArgumentException("해당 유저는 존재하지 않습니다"));
        post.connectUser(user);
        Post addpost = postRepository.save(post);
        return new PostResponseDto(addpost);
    }

    //payload에 들어갈 정보들이 claim이라고 불린다
    private String getUsername(String token) {
        Claims info = jwtUtil.getUserInfoFromToken(token);
        String username = info.getSubject();
        return username;
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postrequestDto, String token) {
        Post post = findPost(id);

        String username = getUsername(token);

        if(!post.getUsername().equals(username)){
            throw new IllegalArgumentException("작성자가 아닙니다");
        }
        post.update(postrequestDto);

        return new PostResponseDto(post);
    }

    private boolean checkPassword(int a, int b) {
        return a==b;
    }

    // ResponseStatusExceptionResolver -> . @ResponseStatus 가 달려있는 예외 , ResponseStatusException 예외 두가지를 처리
    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시글이 존재하지 않습니다.")
        );
    }

    // delete와 deleteById의 차이점
    public String deletePost(Long id, PostRequestDto postRequestDto, String token) {
        Post post = findPost(id);
        String username = getUsername(token);
        if(!post.getUsername().equals(username)){
            throw new IllegalArgumentException("작성자가 아닙니다");
        }
        postRepository.delete(post);

        return "Success";
    }
}
