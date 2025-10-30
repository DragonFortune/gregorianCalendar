package com.example.calendar.input;

import java.util.Scanner;

public class YearReader implements InputReader<Integer> {
    private final Scanner scanner;

    public YearReader(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Integer read(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int year = Integer.parseInt(input);
                if (year > 1600) return year;
                System.out.println("Григорианский календарь корректен только для годов начиная с 1600!");
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод! Введите число.");
            }
        }
    }
}
