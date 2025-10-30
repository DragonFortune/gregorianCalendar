package com.example.calendar.input;

import java.util.Scanner;

public class FormatReader implements InputReader<String> {
    private final Scanner scanner;

    public FormatReader(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String read(String prompt) {
        while (true) {
            System.out.print(prompt + " (txt/csv): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("txt") || input.equals("csv")) {
                return input;
            }
            System.out.println("Некорректный формат! Введите 'txt' или 'csv'.");
        }
    }
}
