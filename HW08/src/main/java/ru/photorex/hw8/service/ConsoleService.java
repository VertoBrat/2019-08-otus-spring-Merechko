package ru.photorex.hw8.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class ConsoleService implements IOService {

    private final ConsoleContext consoleContext;

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
    public void printFormattedString(String s, String name, int result) {
        consoleContext.getPrintStream().printf(s + System.lineSeparator(), name, result);
    }
}
