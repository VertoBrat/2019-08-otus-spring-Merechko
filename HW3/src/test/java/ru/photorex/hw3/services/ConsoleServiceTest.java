package ru.photorex.hw3.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с консолью")
@SpringBootTest
public class ConsoleServiceTest {

    private static final String TEST_STRING = "test string";

    @Autowired
    private IOService console;

    @MockBean
    private ConsoleContext consoleContext;

    @Test
    @DisplayName("должен читать строку из InputStream")
    void shouldReturnTestStringFromInputStream() {
        given(consoleContext.getInputStream()).willReturn(new ByteArrayInputStream(TEST_STRING.getBytes()));
        assertThat(TEST_STRING).isEqualTo(console.readString());
    }

    @Test
    @DisplayName("должен записывать строку в OutputStream")
    void shouldSaveTestStringIntoOutputStream() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        given(consoleContext.getPrintStream()).willReturn(new PrintStream(out));
        console.printString(TEST_STRING);
        assertThat(TEST_STRING + "\r\n")
                .isEqualTo(out.toString());
    }
}
