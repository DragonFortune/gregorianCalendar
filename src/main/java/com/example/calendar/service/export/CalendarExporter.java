package com.example.calendar.service.export;

import com.example.calendar.model.Calendar;

import java.io.IOException;

public interface CalendarExporter {
    void export(Calendar calendar, String fileName) throws IOException;
}
