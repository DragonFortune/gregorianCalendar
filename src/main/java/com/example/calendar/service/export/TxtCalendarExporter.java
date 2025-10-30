package com.example.calendar.service.export;

import com.example.calendar.model.Calendar;
import com.example.calendar.service.format.CalendarFormatter;

import java.io.FileWriter;
import java.io.IOException;

public class TxtCalendarExporter implements CalendarExporter {
    private final CalendarFormatter formatter;

    public TxtCalendarExporter(CalendarFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void export(Calendar calendar, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName + ".txt")) {
            writer.write(formatter.format(calendar));
        }
    }
}
