package com.sparta.postproject.dto;

import com.sparta.postproject.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

//private 으로 선언해주고 getter을 사용해서 값에 접근하는 이유 : 캡슐화, 자료보호
//Dto를 사용하는 이유 ? -> 다양한 이유가 있다 보안 , 정보의 가공이 가능

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;

    private String username;
    private LocalDateTime createAt;

    private List<CommentResponseDto> commentResponseDto;


    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUsername();
        this.createAt = post.getCreatedAt();
        this.commentResponseDto = post.getCommentList().stream().map(CommentResponseDto::new).toList();
    }
}

