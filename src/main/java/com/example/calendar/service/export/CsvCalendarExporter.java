package com.example.calendar.service.export;

import com.example.calendar.model.Calendar;
import com.example.calendar.service.format.CalendarFormatter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class CsvCalendarExporter implements CalendarExporter {
    private final CalendarFormatter formatter;

    public CsvCalendarExporter(CalendarFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void export(Calendar calendar, String fileName) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(fileName + ".csv"), StandardCharsets.UTF_8)) {
            writer.write('\uFEFF');
            writer.write(formatter.format(calendar));
        }
    }
}
