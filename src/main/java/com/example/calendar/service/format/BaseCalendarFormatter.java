package com.example.calendar.service.format;

import com.example.calendar.model.Calendar;
import com.example.calendar.model.DayOfWeek;
import com.example.calendar.model.Month;

public abstract class BaseCalendarFormatter implements CalendarFormatter {
    protected static final String[] DAYS = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};

    protected abstract String separator();

    protected abstract String header(Calendar calendar);

    @Override
    public String format(Calendar calendar) {
        StringBuilder sb = new StringBuilder();
        sb.append(header(calendar));

        int startDay = calendar.getYear().getFirstDayOfYear();

        for (Month month : calendar.getMonths()) {
            sb.append(formatMonth(month, startDay));
            sb.append("\n\n");
            startDay = (startDay + month.daysCount()) % 7;
        }

        return sb.toString();
    }

    private String formatMonth(Month month, int startDay) {
        StringBuilder sb = new StringBuilder();
        sb.append(month.name()).append("\n");

        for (String day : DAYS) sb.append(day).append(separator());
        sb.append("\n");

        int dayOfWeek = startDay;
        sb.append(separator().repeat(dayOfWeek));

        for (int day = 1; day <= month.daysCount(); day++) {
            sb.append(day).append(separator());
            dayOfWeek = DayOfWeek.nextDay(dayOfWeek);
            if (dayOfWeek == 0) sb.append("\n");
        }

        return sb.toString();
    }
}