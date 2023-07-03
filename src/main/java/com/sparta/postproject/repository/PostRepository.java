package com.sparta.postproject.repository;

import com.sparta.postproject.entity.Post;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll();

    Post save(Post post);
}
