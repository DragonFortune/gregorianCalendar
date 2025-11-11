package com.example.calendar.service;

import com.example.calendar.entity.CalendarEntity;
import com.example.calendar.exception.types.InvalidYearException;
import com.example.calendar.exception.types.NotFoundException;
import com.example.calendar.model.Calendar;
import com.example.calendar.repository.CalendarRepository;
import com.example.calendar.service.format.CalendarFormatter;
import com.example.calendar.service.impl.CalendarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class CalendarServiceImplTest {

    @Mock
    private CalendarRepository calendarRepository;

    @Mock
    private CalendarFormatter txtFormatter;

    @Mock
    private CalendarFormatter csvFormatter;

    @InjectMocks
    private CalendarServiceImpl calendarService;

    @BeforeEach
    void setUp() {
        Map<String, CalendarFormatter> formatters = Map.of(
                "txt", txtFormatter,
                "csv", csvFormatter
        );
        ReflectionTestUtils.setField(calendarService, "formatters", formatters);
    }

    @Test
    void createNewCalendar() {
        int year = 2025;

        when(calendarRepository.findByYear(year)).thenReturn(Optional.empty());
        when(txtFormatter.format(any(Calendar.class))).thenReturn("Generated content");
        when(calendarRepository.save(any(CalendarEntity.class)))
                .thenReturn(new CalendarEntity(1L, year, "Generated content"));

        CalendarEntity result = calendarService.generateAndSave(year);

        assertEquals(year, result.getYear());
        assertEquals("Generated content", result.getContent());
    }

    @Test
    void invalidYearThrowsException() {
        assertThrows(InvalidYearException.class, () -> calendarService.generateAndSave(1500));
    }

    @Test
    void getCalendarById() {
        Long id = 1L;
        CalendarEntity entity = new CalendarEntity(id, 2025, "test content");
        when(calendarRepository.findById(id)).thenReturn(Optional.of(entity));

        String result = calendarService.getById(id);

        assertEquals("test content", result);
    }

    @Test
    void getCalendarByIdNotFound() {
        when(calendarRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> calendarService.getById(1L));
    }

    @Test
    void downloadCalendarFile() {
        Long id = 1L;
        CalendarEntity entity = new CalendarEntity(id, 2025, "test content");

        when(calendarRepository.findById(id)).thenReturn(Optional.of(entity));
        when(txtFormatter.format(any(Calendar.class))).thenReturn("Formatted content");

        ResponseEntity<byte[]> response = calendarService.downloadCalendar(id, "txt");

        assertNotNull(response);
        assertTrue(new String(Objects.requireNonNull(response.getBody())).contains("Formatted content"));
        assertTrue(Objects.requireNonNull(response.getHeaders().getFirst("Content-Disposition")).contains("2025"));
    }

    @Test
    void downloadCalendarNotFound() {
        when(calendarRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> calendarService.downloadCalendar(1L, "txt"));
    }
}
