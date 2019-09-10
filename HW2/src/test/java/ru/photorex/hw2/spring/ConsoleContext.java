package ru.photorex.hw2.spring;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class ConsoleContext {

    public static final String TEST_STRING = "test";

    private InputStream inputStream;
    private PrintStream printStream;
    private ByteArrayOutputStream outputStream;

    public ConsoleContext() {
        inputStream = new ByteArrayInputStream(TEST_STRING.getBytes());
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
    }

    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }
}
