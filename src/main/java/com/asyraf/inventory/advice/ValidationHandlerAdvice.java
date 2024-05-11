package com.asyraf.inventory.advice;

import com.asyraf.inventory.dto.responses.BaseResponseDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationHandlerAdvice {

        @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDto> validationExceptionHandler(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(BaseResponseDto.builder()
                .failure(true)
                .message("Request is not correct")
                .data(e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage))
                .build(),
                HttpStatus.BAD_REQUEST);
    }

}