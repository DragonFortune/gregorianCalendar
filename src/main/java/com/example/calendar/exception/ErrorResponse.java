package com.example.calendar.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Data
public class ErrorResponse {

    private int errorCode;
    private String errorMessage;
    private LocalDateTime timestamp;
}