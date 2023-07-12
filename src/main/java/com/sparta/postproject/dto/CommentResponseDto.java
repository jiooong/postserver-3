package com.sparta.postproject.dto;

import com.sparta.postproject.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String content;

    public CommentResponseDto(Comment comment){
        this.content=comment.getContent();

    }
}
