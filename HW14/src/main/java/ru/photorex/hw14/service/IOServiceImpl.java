package ru.photorex.hw14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IOServiceImpl implements IOService {

    private final ConsoleContext consoleContext;

    @Override
    public void printString(String s) {
        consoleContext.getPrintStream().println(s);
    }
}
