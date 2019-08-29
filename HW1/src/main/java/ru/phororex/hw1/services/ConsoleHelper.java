package ru.phororex.hw1.services;

import ru.phororex.hw1.model.Question;

import java.util.Scanner;

public class ConsoleHelper {

    public static int print(Question q) {

        System.out.println(q.getTheQuestion());
        Scanner sc = new Scanner(System.in);
        if (sc.nextLine().toLowerCase().equals(q.getAnswer())) {
            System.out.println("Верно");
            return 1;
        } else {
            System.out.printf("Нет, правильный ответ \"%s\"\n", q.getAnswer());
            return 0;
        }
    }
}
