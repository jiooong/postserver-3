package com.sparta.postproject.controller;

import com.sparta.postproject.dto.PostRequestDto;
import com.sparta.postproject.dto.PostResponseDto;
import com.sparta.postproject.entity.Post;
import com.sparta.postproject.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//의존성 주입의 3가지 방법 -> 생성자 주입, 필드 주입, 수정자 주입
//클래스의 생성자가 하나이고, 그 생성자로 주입받을 객체가 빈으로 등록되어 있다면  @Autowired를 생략 할 수 있습니다.
@RestController
@RequestMapping("/api")
public class PostController {
    // 1번째 방법 : 생성자 주입
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /*2번쨰 방법 : 필드 주입 @Autowired 어노테이션을 붙여주면 자동으로 의존성 주입이 된다.
     코드가 간결하고 간단하지만 외부에서 변경이 힘들고 , 프레임워크에 의존적이고 객체지향적으로 좋지 않다.
        @Autowired
        private PostService postService; */

    /*3번쨰 방법 : 수정자 주입(Setter Injection) 은 Setter 메소드에 @Autowired어노테이션을 붙이는 방법
    수정자 주입을 사용하면 setXXX 메서드를 public으로 열어두어야 하기 때문에 언제 어디서든 변경이 가능하다.
    @Autowired
    public void setPostService(Postservice postService){
      this.postService = postService;
    */
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(){
        return postService.findAll();
    }

    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto)
    {
        return postService.createPost(postRequestDto);

    }

    @GetMapping("/post/{id}")
    public Optional<Post> getPost(@PathVariable("id") Long id ){
        return postService.getPostById(id);
    }

    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable("id") Long id, @RequestBody PostRequestDto postrequestDto){
        return postService.updatePost(id, postrequestDto);

    }
/*
    @DeleteMapping("/post/{id}")
    public PostResponseDto deletePost(@PathVariable("id") Long id ){

    }
    */

}
