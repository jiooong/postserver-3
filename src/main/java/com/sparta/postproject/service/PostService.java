package com.sparta.postproject.service;

import com.sparta.postproject.dto.PostRequestDto;
import com.sparta.postproject.dto.PostResponseDto;
import com.sparta.postproject.entity.Post;
import com.sparta.postproject.repository.PostRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostResponseDto> findAll() {

        //return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream().map(PostResponseDto::new).toList();
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();

    }

    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        Post addpost = postRepository.save(post);
        return new PostResponseDto(addpost);

    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public PostResponseDto updatePost(Long id, PostRequestDto postrequestDto) {
        Post post = findPost(id);
        if(checkPassword(post.getPassword(), postrequestDto.getPassword())){
            post.update(postrequestDto);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다.");
        }

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
    public String deletePost(Long id, PostRequestDto postRequestDto) {
        Post post = findPost(id);;
        if(post.getPassword()==postRequestDto.getPassword()){
            postRepository.delete(post);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다.");
        }
        return "Success";
    }
}
