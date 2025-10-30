package com.example.calendar.model;

public class Year {
    private final int year;
    private final boolean isLeap;

    public Year(int year) {
        this.year = year;
        this.isLeap = isLeapYear(year);
    }

    public int getYear() {
        return year;
    }

    public boolean isLeap() {
        return isLeap;
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public int getFirstDayOfYear() {
        int day = 1, month = 1, y = year;
        if (month < 3) {
            month += 12;
            y--;
        }
        int K = y % 100;
        int J = y / 100;
        int h = (day + (13 * (month + 1)) / 5 + K + K / 4 + J / 4 + 5 * J) % 7;
        return (h + 5) % 7;
    }
}
