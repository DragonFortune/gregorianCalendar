package com.example.calendar.service.impl;

import com.example.calendar.entity.CalendarEntity;
import com.example.calendar.exception.types.InvalidYearException;
import com.example.calendar.exception.types.NotFoundException;
import com.example.calendar.model.Calendar;
import com.example.calendar.model.Year;
import com.example.calendar.repository.CalendarRepository;
import com.example.calendar.service.CalendarService;
import com.example.calendar.service.format.CalendarFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final Map<String, CalendarFormatter> formatters;

    @Override
    @Transactional
    public CalendarEntity generateAndSave(int year) {
        if (year < 1600) {
            log.error("Попытка создать календарь для года {} ниже 1600", year);
            throw new InvalidYearException("Год должен быть >= 1600");
        }

        log.info("Генерация календаря для {} года", year);
        return calendarRepository.findByYear(year)
                .orElseGet(() -> {
                    log.info("Календарь для {} года не найден, создаем новый", year);

                    Calendar calendar = new Calendar(new Year(year));
                    CalendarFormatter formatter = formatters.get("txt");
                    String content = formatter.format(calendar);

                    CalendarEntity entity = CalendarEntity.builder()
                            .year(year)
                            .content(content)
                            .build();

                    return calendarRepository.save(entity);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public String getById(Long id, String format) {
        log.info("Получение календаря с id {}", id);

        CalendarEntity entity = calendarRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Календарь с id {} не найден", id);
                    return new NotFoundException("Календарь не найден с id " + id);
                });

        return entity.getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> downloadCalendar(Long id, String format) {
        log.info("Подготовка файла календаря id={} для скачивания в формате {}", id, format);

        CalendarEntity entity = calendarRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Календарь не найден с id " + id));

        Calendar calendar = new Calendar(new Year(entity.getYear()));
        CalendarFormatter formatter = formatters.getOrDefault(format.toLowerCase(), formatters.get("txt"));
        String content = formatter.format(calendar);

        MediaType mediaType = "csv".equalsIgnoreCase(format)
                ? MediaType.parseMediaType("text/csv")
                : MediaType.TEXT_PLAIN;

        String filename = "calendar_" + entity.getYear() + "." + format.toLowerCase();
        log.info("Файл {} подготовлен для отправки клиенту", filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(mediaType)
                .body(content.getBytes());
    }
}
