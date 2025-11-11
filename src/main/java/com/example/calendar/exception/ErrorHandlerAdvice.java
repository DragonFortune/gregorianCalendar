package com.example.calendar.exception;

import com.example.calendar.exception.types.InvalidYearException;
import com.example.calendar.exception.types.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j(topic = "ErrorHandlerAdvice")
public class ErrorHandlerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        log.error("404 Not Found: {}", ex.getMessage(), ex);
        ErrorResponse response = ErrorResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .errorMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidYearException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidYearException(InvalidYearException ex) {
        log.error("400 Bad Request: {}", ex.getMessage());
        ErrorResponse response = ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response).getBody();
    }
}
