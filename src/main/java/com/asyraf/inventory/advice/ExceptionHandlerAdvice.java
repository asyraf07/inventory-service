package com.asyraf.inventory.advice;

import com.asyraf.inventory.dto.responses.BaseResponseDto;
import com.asyraf.inventory.exception.BadRequestException;
import com.asyraf.inventory.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponseDto> notFoundExceptionHadler(NotFoundException e) {
        return new ResponseEntity<>(BaseResponseDto.builder()
                .failure(true)
                .message(e.getMessage())
                .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponseDto> badRequestExceptionHandler(BadRequestException e) {
        return new ResponseEntity<>(BaseResponseDto.builder()
                .failure(true)
                .message(e.getMessage())
                .build(),
                HttpStatus.BAD_REQUEST);
    }
}
