package com.sparta.postproject.repository;

import com.sparta.postproject.entity.Post;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


import java.util.List;
import java.util.Optional;

//내림차순 정렬 방법 2가지
//1. sort 방식 : List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
//2. Repository에 인터페이스 정의하기()
@EnableJpaAuditing
@SpringBootApplication
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();

    Post save(Post post);
    Optional<Post> findById(Long id);
}

