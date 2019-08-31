package ru.phororex.hw1.services;

import ru.phororex.hw1.model.Question;

import java.util.Scanner;

public class ConsoleService implements IOService {

    public int printQuestions(Question question) {
        printString(question.getTheQuestion());
        if (readString().toLowerCase().equals(question.getAnswer())) {
            printString("Верно");
            return 1;
        } else {
            printString("Нет, правильный ответ " + question.getAnswer());
            return 0;
        }
    }

    @Override
    public String readString() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    @Override
    public void printString(String s) {
        System.out.println(s);
    }
}
