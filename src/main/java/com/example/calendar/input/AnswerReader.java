package com.example.calendar.input;

import java.util.Scanner;

public class AnswerReader implements InputReader<Boolean> {
    private final Scanner scanner;

    public AnswerReader(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Boolean read(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
                case "y": return true;
                case "n": return false;
                default: System.out.println("Введите 'y' или 'n'.");
            }
        }
    }
}
