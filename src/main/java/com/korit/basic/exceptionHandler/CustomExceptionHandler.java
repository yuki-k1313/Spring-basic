package com.korit.basic.exceptionHandler;

import java.lang.reflect.Method;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice : 해당 클래스를 RestController에서 발생하는 특정 상황들에 대해 처리를 담당하는 클래스로 지정

@RestControllerAdvice
public class CustomExceptionHandler {

    // @ExceptionHandler : 지정한 예외에 대하여 직접 조작할 수 있도록 하는 어노테이션
    // @ExceptionHandler(value={예외클래스, ...})
    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    public ResponseEntity<String> customException(
        MethodArgumentNotValidException exception
    ) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("잘못된 입력입니다.");
    }
    
}