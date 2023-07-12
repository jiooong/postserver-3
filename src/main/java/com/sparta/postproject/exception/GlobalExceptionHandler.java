package com.sparta.postproject.exception;

import com.sparta.postproject.dto.StatusCodeResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<StatusCodeResponseDto> handleException(IllegalArgumentException ex) {
        StatusCodeResponseDto statusCodeResponseDto = new StatusCodeResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                statusCodeResponseDto,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}