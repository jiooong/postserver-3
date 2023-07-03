package com.sparta.postproject.entity;

import com.sparta.postproject.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "post")
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "content", nullable = false)
    String content;

    @Column(name = "password")
    int password;

    @Column(name = "localDateTime")
    LocalDateTime localDateTime;

    public Post(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.password = postRequestDto.getPassword();
    }

    public String getLocaldatetime() {
        return String.valueOf(localDateTime);
    }

    public void update(PostRequestDto postrequestDto) {
        this.content = postrequestDto.getContent();
        this.title = postrequestDto.getTitle();
    }
}
