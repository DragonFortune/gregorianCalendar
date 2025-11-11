package com.example.calendar.controller;

import com.example.calendar.entity.CalendarEntity;
import com.example.calendar.service.CalendarService;
import com.example.calendar.util.CounterMetric;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @CounterMetric(name = "calendar.create", tags = {"endpoint", "create"})
    @PostMapping
    public ResponseEntity<CalendarEntity> create(
            @RequestParam int year) {
        log.info("POST /api/v1/calendar вызван с параметром year={}", year);
        CalendarEntity saved = calendarService.generateAndSave(year);
        log.info("Календарь для года {} создан с id {}", year, saved.getId());
        return ResponseEntity.ok(saved);
    }

    @CounterMetric(name = "calendar.get", tags = {"endpoint", "get"})
    @GetMapping("/{id}")
    public ResponseEntity<String> getCalendar(@PathVariable Long id) {
        log.info("GET /api/v1/calendar/{} вызван", id);
        String content = calendarService.getById(id);
        log.info("Календарь с id {} получен", id);
        return ResponseEntity.ok(content);
    }

    @CounterMetric(name = "calendar.download", tags = {"endpoint", "download"})
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadCalendar(@PathVariable Long id,
                                                   @RequestParam(defaultValue = "txt") String format) {
        log.info("GET /api/v1/calendar/{}/download?format={} вызван", id, format);
        return calendarService.downloadCalendar(id, format);
    }
}
