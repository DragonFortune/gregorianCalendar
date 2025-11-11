package com.example.calendar.exception.types;

public class InvalidYearException extends RuntimeException {
    public InvalidYearException(String message) {
        super(message);
    }
}
