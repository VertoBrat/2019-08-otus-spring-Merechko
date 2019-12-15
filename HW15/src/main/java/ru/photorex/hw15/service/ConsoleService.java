package ru.photorex.hw15.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsoleService implements IOService {

    private final ConsoleContext consoleContext;

    @Override
    public void printString(String s) {
        consoleContext.getPrintStream().println(s);
    }

    @Override
    public void printFormattedString(String s, Object...objects) {
        consoleContext.getPrintStream().printf(s + System.lineSeparator(), objects);
    }
}
