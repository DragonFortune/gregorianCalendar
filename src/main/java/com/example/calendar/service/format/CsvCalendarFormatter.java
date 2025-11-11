package com.example.calendar.service.format;

import com.example.calendar.model.Calendar;
import org.springframework.stereotype.Component;

@Component("csv")
public class CsvCalendarFormatter extends BaseCalendarFormatter {

    @Override
    protected String separator() {
        return ";";
    }

    @Override
    protected String header(Calendar calendar) {
        return "Месяц;" + String.join(";", DAYS) + "\n";
    }
}
