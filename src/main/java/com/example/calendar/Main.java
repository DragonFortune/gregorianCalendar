package com.example.calendar;

import com.example.calendar.input.AnswerReader;
import com.example.calendar.input.FormatReader;
import com.example.calendar.input.InputReader;
import com.example.calendar.input.YearReader;
import com.example.calendar.model.Year;
import com.example.calendar.model.Calendar;
import com.example.calendar.service.export.CalendarExporter;
import com.example.calendar.service.export.CsvCalendarExporter;
import com.example.calendar.service.export.TxtCalendarExporter;
import com.example.calendar.service.format.CalendarFormatter;
import com.example.calendar.service.format.CsvCalendarFormatter;
import com.example.calendar.service.format.TxtCalendarFormatter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CalendarFormatter formatterTxt = new TxtCalendarFormatter();
        CalendarFormatter formatterCsv = new CsvCalendarFormatter();
        InputReader<Integer> yearReader = new YearReader(scanner);
        InputReader<Boolean> answerReader = new AnswerReader(scanner);
        InputReader<String> formatReader = new FormatReader(scanner);

        do {
            int inputYear = yearReader.read("Введите год: ");
            Year year = new Year(inputYear);
            Calendar calendar = new Calendar(year);
            System.out.println(formatterTxt.format(calendar));

            if (answerReader.read("Хотите сохранить календарь в файл? y/n: ")) {
                String format = formatReader.read("Выберите формат");
                CalendarExporter exporter = switch (format) {
                    case "txt" -> new TxtCalendarExporter(formatterTxt);
                    case "csv" -> new CsvCalendarExporter(formatterCsv);
                    default -> throw new IllegalArgumentException("Неизвестный формат: " + format);
                };

                try {
                    exporter.export(calendar, "calendar_" + inputYear);
                    System.out.println("Файл сохранён успешно!");
                } catch (Exception e) {
                    System.out.println("Ошибка при сохранении: " + e.getMessage());
                }
            }

        } while (answerReader.read("Хотите продолжить? y/n: "));

        scanner.close();
    }
}
