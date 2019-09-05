package ru.photorex.hw2.services.utils;

import ru.photorex.hw2.services.ConsoleService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class ConsoleServiceHelper {

    public static ByteArrayOutputStream output;

    private ConsoleServiceHelper(){}

    public static ConsoleService getInstance(String testString) {
        InputStream in = new ByteArrayInputStream(testString.getBytes());
        output = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(output);
        return new ConsoleService(in, out);
    }
}
