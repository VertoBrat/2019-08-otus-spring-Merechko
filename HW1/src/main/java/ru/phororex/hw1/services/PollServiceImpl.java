package ru.phororex.hw1.services;


import ru.phororex.hw1.NoCsvDataException;
import ru.phororex.hw1.model.Question;

import java.util.List;
import java.util.Scanner;

public class PollServiceImpl implements PollService {

    private final CSVDataParser parser;

    public PollServiceImpl(CSVDataParser parser) {
        this.parser = parser;
    }

    @Override
    public void startPollWithCSVdata() {
        List<Question> questions;
        int result;
        String name;
        try (Scanner sc = new Scanner(System.in)) {
            questions = parser.parseQuestions();
            System.out.println("Введите имя и фамилию");
            name = sc.nextLine();
            result = questions.stream().mapToInt(ConsoleHelper::print).sum();
            System.out.printf("%s дал %d правильных ответа", name, result);
        } catch (NoCsvDataException ex) {
            System.out.print(ex.getLocalizedMessage());
        }

    }
}
