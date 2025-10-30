package com.example.calendar.service.format;

import com.example.calendar.model.Calendar;
import com.example.calendar.model.Month;

public class CsvCalendarFormatter implements CalendarFormatter {
    private static final String[] DAYS = {"Пн","Вт","Ср","Чт","Пт","Сб","Вс"};

    @Override
    public String format(Calendar calendar) {
        StringBuilder sb = new StringBuilder();
        sb.append("Месяц; ;").append(String.join(";", DAYS)).append("\n");

        int startDay = calendar.getYear().getFirstDayOfYear();

        for (Month month : calendar.getMonths()) {
            sb.append(month.name()).append(";;");

            int dayOfWeek = startDay;

            sb.append(";".repeat(Math.max(0, dayOfWeek)));

            for (int d = 1; d <= month.daysCount(); d++) {
                sb.append(d);

                if (dayOfWeek < 6) {
                    sb.append(";");
                }

                dayOfWeek = (dayOfWeek + 1) % 7;

                if (dayOfWeek == 0 && d != month.daysCount()) {
                    sb.append("\n;;");
                }
            }

            if (dayOfWeek != 0) {
                for (int i = dayOfWeek; i < 7; i++) {
                    sb.append(";");
                }
            }

            sb.append("\n\n");
            startDay = dayOfWeek;
        }

        return sb.toString();
    }
}
