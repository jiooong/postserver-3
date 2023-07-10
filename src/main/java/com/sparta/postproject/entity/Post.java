package com.sparta.postproject.entity;

import com.sparta.postproject.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "post")
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "content", nullable = false)
    String content;

   @Column(name = "username", nullable = false)
    String username;



    public Post(PostRequestDto postRequestDto, String username) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.username = postRequestDto.getUsername();
    }



    public void update(PostRequestDto postrequestDto) {
        this.content = postrequestDto.getContent();
        this.title = postrequestDto.getTitle();
    }
}
