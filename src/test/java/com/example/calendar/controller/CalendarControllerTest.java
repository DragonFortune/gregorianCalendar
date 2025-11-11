package com.example.calendar.controller;

import com.example.calendar.entity.CalendarEntity;
import com.example.calendar.service.CalendarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;


public class CalendarControllerTest {

    @Mock
    private CalendarService calendarService;

    @Mock
    private CalendarController calendarController;

    @BeforeEach
    void setUp() {
        calendarService = Mockito.mock(CalendarService.class);
        calendarController = new CalendarController(calendarService);
    }

    @Test
    void createCalendar() {
        int year = 2025;
        CalendarEntity entity = new CalendarEntity(1L, year, "Test content");

        when(calendarService.generateAndSave(year)).thenReturn(entity);

        ResponseEntity<CalendarEntity> response = calendarController.create(year);

        assertEquals(OK, response.getStatusCode());
        assertEquals(year, Objects.requireNonNull(response.getBody()).getYear());
        verify(calendarService).generateAndSave(year);
    }

    @Test
    void getCalendarById() {
        Long id = 1L;
        when(calendarService.getById(id)).thenReturn("Test content");

        ResponseEntity<String> response = calendarController.getCalendar(id);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Test content", response.getBody());
        verify(calendarService).getById(id);
    }

    @Test
    void downloadCalendar() {
        Long id = 1L;
        String format = "txt";
        byte[] fileData = "File content".getBytes();

        ResponseEntity<byte[]> expectedResponse = ResponseEntity.ok(fileData);
        when(calendarService.downloadCalendar(id, format)).thenReturn(expectedResponse);

        ResponseEntity<byte[]> response = calendarController.downloadCalendar(id, format);

        assertEquals(OK, response.getStatusCode());
        assertArrayEquals(fileData, response.getBody());
        verify(calendarService).downloadCalendar(id, format);
    }
}
