package ru.photorex.hw2.plain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.photorex.hw2.services.ConsoleService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с консолью")
public class ConsoleServiceTest {
    private static final String TEST_STRING = "test string";


    private InputStream in;
    private PrintStream out;
    private ByteArrayOutputStream outputStream;
    private ConsoleService cs;

    @BeforeEach
    void setUp() {
        in = new ByteArrayInputStream(TEST_STRING.getBytes());
        outputStream = new ByteArrayOutputStream();
        out = new PrintStream(outputStream);
        cs = new ConsoleService(in, out);
    }

    @Test
    @DisplayName("должен читать строку из InputStream")
    void shouldReturnTestStringFromInputStream() {
        assertThat(TEST_STRING).isEqualTo(cs.readString());
    }

    @Test
    @DisplayName("должен записывать строку в OutputStream")
    void shouldSaveTestStringIntoOutputStream() {
        cs.printString(TEST_STRING);
        assertThat(TEST_STRING  + "\r\n").isEqualTo(outputStream.toString());
    }
}
