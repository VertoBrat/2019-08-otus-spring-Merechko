package ru.photorex.hw2.plain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.photorex.hw2.services.ConsoleContext;
import ru.photorex.hw2.services.ConsoleService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с консолью")
public class ConsoleServiceTest {
    private static final String TEST_STRING = "test string";

    private ConsoleService cs;
    private ConsoleContext cc;

    @BeforeEach
    void setUp() {
        InputStream in = new ByteArrayInputStream(TEST_STRING.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);
        cc = new ConsoleContext();
        cc.setPrintStream(out);
        cc.setInputStream(in);
        cc.setOutputStream(outputStream);
        cs = new ConsoleService(cc);
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
        assertThat(TEST_STRING + "\r\n").isEqualTo(cc.getOutputStream().toString());
    }
}
