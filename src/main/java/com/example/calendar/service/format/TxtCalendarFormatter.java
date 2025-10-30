package com.example.calendar.service.format;

import com.example.calendar.model.DayOfWeek;
import com.example.calendar.model.Month;
import com.example.calendar.model.Calendar;

public class TxtCalendarFormatter implements CalendarFormatter {
    private static final String[] DAYS = {"Пн","Вт","Ср","Чт","Пт","Сб","Вс"};

    @Override
    public String format(Calendar calendar) {
        StringBuilder sb = new StringBuilder();
        int startDay = calendar.getYear().getFirstDayOfYear();

        sb.append("Календарь на ").append(calendar.getYear().getYear()).append("\n");

        for (Month month : calendar.getMonths()) {
            sb.append("\n").append(month.name()).append("\n");
            for (String d : DAYS) sb.append(d).append("\t");
            sb.append("\n");

            int dayOfWeek = startDay;
            for (int i = 0; i < dayOfWeek; i++) sb.append("\t");

            for (int day = 1; day <= month.daysCount(); day++) {
                sb.append(day).append("\t");
                dayOfWeek = DayOfWeek.nextDay(dayOfWeek);
                if (dayOfWeek == 0) sb.append("\n");
            }
            sb.append("\n");
            startDay = dayOfWeek;
        }
        return sb.toString();
    }
}
