package com.sparta.postproject.repository;


import com.sparta.postproject.entity.Comment;
import com.sparta.postproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository  extends JpaRepository<Comment, Long> {
   Optional<Comment> findById(Long id);
}
