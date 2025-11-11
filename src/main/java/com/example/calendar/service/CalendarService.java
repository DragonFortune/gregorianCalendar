package com.example.calendar.service;

import com.example.calendar.entity.CalendarEntity;
import org.springframework.http.ResponseEntity;

public interface CalendarService {
    CalendarEntity generateAndSave(int year);
    String getById(Long id, String format);
    ResponseEntity<byte[]> downloadCalendar(Long id, String format);
}
