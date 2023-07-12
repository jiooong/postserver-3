package com.sparta.postproject.controller;

import com.sparta.postproject.dto.CommentRequestDto;
import com.sparta.postproject.dto.CommentResponseDto;
import com.sparta.postproject.dto.StatusCodeResponseDto;
import com.sparta.postproject.service.CommentService;
import com.sparta.postproject.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    public CommentController(CommentService commentService, JwtUtil jwtUtil) {

        this.commentService = commentService;
        this.jwtUtil = jwtUtil;

    }

    @PostMapping("/comment")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, HttpServletRequest req){
        String token = authentication(req);
        return commentService.createComment(commentRequestDto, token);
    }

    @DeleteMapping("/comment/{id}")
    public StatusCodeResponseDto deleteComment(@PathVariable("id") Long id , @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest req){
        String token = authentication(req);
        return commentService.deleteComment(id, commentRequestDto, token);
    }

    private String authentication(HttpServletRequest req) {
        String tokenValue = jwtUtil.getTokenFromRequest(req);
        String token = jwtUtil.substringToken(tokenValue);

        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("Token Error");
        }
        return token;
    }

    @PutMapping("/comments/{id}")
    public CommentResponseDto updateComment(@PathVariable("id") Long id,
                                            @RequestBody CommentRequestDto commentRequestDto,
                                            HttpServletRequest req){
        String token = authentication(req);
        return commentService.updateComment(id, commentRequestDto, token);
    }
}

