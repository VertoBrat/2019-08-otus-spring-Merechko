package ru.photorex.hw3.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.photorex.hw3.TestAppConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.photorex.hw3.TestAppConfig.TEST_STRING;

@DisplayName("Сервис для работы с консолью")
@SpringBootTest(classes = {TestAppConfig.class})
public class ConsoleServiceTest {

    @Autowired
    private IOService console;

    @Autowired
    private ConsoleContext consoleContext;

    @Test
    @DisplayName("должен читать строку из InputStream")
    void shouldReturnTestStringFromInputStream() {
        assertThat(TEST_STRING).isEqualTo(console.readString());
    }

    @Test
    @DisplayName("должен записывать строку в OutputStream")
    void shouldSaveTestStringIntoOutputStream() {
        console.printString(TEST_STRING);
        assertThat(TEST_STRING + "\r\n")
                .isEqualTo(consoleContext.getOutputStream().toString());
    }
}
