package com.sparta.postproject.exception;

import com.sparta.postproject.dto.StatusCodeResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  //컨트롤러에서 발생하는 모든 예외처리를 다 알수있음
public class Exceptions {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<StatusCodeResponseDto> IllegalArgumentExceptionHandler(IllegalArgumentException ex) {
        StatusCodeResponseDto restApiException = new StatusCodeResponseDto( HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        return ResponseEntity.badRequest().body(restApiException);
    }

//    @ExceptionHandler({CustomException.class})
//    public ResponseEntity<StatusCodeResponseDto> CustomExceptionHandler(CustomException ex) {
//        StatusCodeResponseDto restApiException = new StatusCodeResponseDto(ex.status.value(),ex.getMessage());
//        return ResponseEntity.status(ex.status).body(restApiException);
//    }
}


//class CustomException extends RuntimeException{
//
//    HttpStatus status;
//
//    public CustomException(HttpStatus status, String message){
//        super(message);
//        this.status = status
//    }
//    public CustomException(int status, String message){
//        super(message);
//        this.status = HttpStatus.valueOf(status);
//    }
//}