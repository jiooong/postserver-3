package com.sparta.postproject.service;

import com.sparta.postproject.dto.CommentRequestDto;
import com.sparta.postproject.dto.CommentResponseDto;
import com.sparta.postproject.dto.PostRequestDto;
import com.sparta.postproject.dto.PostResponseDto;
import com.sparta.postproject.dto.StatusCodeResponseDto;
import com.sparta.postproject.entity.Comment;
import com.sparta.postproject.entity.Post;
import com.sparta.postproject.entity.User;
import com.sparta.postproject.repository.CommentRepository;
import com.sparta.postproject.repository.PostRepository;
import com.sparta.postproject.repository.UserRepository;
import com.sparta.postproject.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, JwtUtil jwtUtil, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;

    }

    public Post findPost(Long id){
        return postRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("선택하신 게시글은 존재하지 않습니다."));
    }

    private String getUsername(String token) {
        Claims info = jwtUtil.getUserInfoFromToken(token);
        String username = info.getSubject();
        return username;
    }

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, String token) {

        String username = getUsername(token);

        User user = userRepository.findByUsername(username).orElseThrow(()->
                new IllegalArgumentException("해당 유저는 존재하지 않습니다"));

        Post post = findPost(commentRequestDto.getPostId());
        Comment comment = new Comment(commentRequestDto);
        comment.connectPost(post);
        comment.connectUser(user);
        Comment addComment = commentRepository.save(comment);
        return new CommentResponseDto(addComment);

    }
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, String token) {
        Comment comment = findComment(id);

        String username = getUsername(token);

        if(!comment.getUser().getUsername().equals(username)){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        comment.update(commentRequestDto);

        return new CommentResponseDto(comment);
    }
    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
    }

    public StatusCodeResponseDto deleteComment(Long id, CommentRequestDto commentRequestDto, String token) {
      // 해당 사용자가 작성한 댓글인지 확인하기
        Comment comment = commentRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 댓글은 존재하지 않습니다"));

        String username = getUsername(token);

        if(!comment.getUser().getUsername().equals(username)){
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);

        return new StatusCodeResponseDto(HttpStatus.OK.value(), "댓글 삭제 성공");
    }
}
