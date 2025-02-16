package com.example.todo_management.exception;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final WebRequest webRequest;

    public GlobalExceptionHandler(@Qualifier("webRequest") WebRequest webRequest) {
        this.webRequest = webRequest;
    }

    @ExceptionHandler(TodoAPIException.class)
    public ResponseEntity<ErrorDetails> handleTodoAPIException(TodoAPIException todoAPIException, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), todoAPIException.getMessage(), request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
