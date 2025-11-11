package com.example.calendar.service.format;

import com.example.calendar.model.Calendar;

public class TxtCalendarFormatter extends BaseCalendarFormatter {

    @Override
    protected String separator() {
        return "\t";
    }

    @Override
    protected String header(Calendar calendar) {
        return "Календарь на " + calendar.getYear().getYear() + "\n\n";
    }
}
