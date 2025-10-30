package com.example.calendar.model;

public record DayOfWeek(int index, String name) {

    public static int nextDay(int currentIndex) {
        return (currentIndex + 1) % 7;
    }
}
