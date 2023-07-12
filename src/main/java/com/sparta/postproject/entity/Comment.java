package com.sparta.postproject.entity;

import com.sparta.postproject.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "comment")
@NoArgsConstructor
@Getter
public class Comment extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    public Comment(CommentRequestDto commentRequestDto) {
       this.content = commentRequestDto.getContent();
    }


    public void connectPost(Post post) {
        this.post = post;
    }


    public void connectUser(User user) {
        this.user = user;
    }
}
