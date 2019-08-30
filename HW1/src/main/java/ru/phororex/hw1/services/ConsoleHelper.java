package ru.phororex.hw1.services;

import ru.phororex.hw1.model.Question;

import java.util.List;
import java.util.Scanner;

public class ConsoleHelper {

    public void printQuestions(List<Question> questions) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите имя и фамилию");
        String name = sc.nextLine();
        int result = 0;
        for (Question q : questions) {
            System.out.println(q.getTheQuestion());
            if (sc.nextLine().toLowerCase().equals(q.getAnswer())) {
                System.out.println("Верно");
                result++;
            } else {
                System.out.printf("Нет, правильный ответ \"%s\"\n", q.getAnswer());
            }
        }
        System.out.printf("%s дал %d правильных ответа", name, result);
    }
}
