package ru.photorex.hw2.services;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ConsoleService implements IOService {

    private final ConsoleContext consoleContext;

    public ConsoleService(ConsoleContext consoleContext) {
        this.consoleContext = consoleContext;
    }

    @Override
    public String readString() {
        Scanner sc = new Scanner(consoleContext.getInputStream());
        return sc.nextLine();
    }

    @Override
    public void printString(String s) {
        consoleContext.getPrintStream().println(s);
    }

    @Override
    public ConsoleContext getConsoleContext() {
        return consoleContext;
    }
}
