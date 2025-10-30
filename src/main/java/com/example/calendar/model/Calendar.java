package com.example.calendar.model;

import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private final Year year;
    private final List<Month> months = new ArrayList<>();

    public Calendar(Year year) {
        this.year = year;
        initMonths();
    }

    private void initMonths() {
        int[] daysInMonth = {31,28,31,30,31,30,31,31,30,31,30,31};
        if (year.isLeap()) daysInMonth[1] = 29;
        String[] monthNames = {"Январь","Февраль","Март","Апрель","Май","Июнь",
                "Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
        for (int i = 0; i < 12; i++) {
            months.add(new Month(monthNames[i], daysInMonth[i]));
        }
    }

    public Year getYear() {
        return year;
    }

    public List<Month> getMonths() {
        return months;
    }
}
