package ru.photorex.hw2.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.photorex.hw2.services.ConsoleService;
import ru.photorex.hw2.services.IOService;
import ru.photorex.hw2.spring.ConsoleContext;
import ru.photorex.hw2.spring.TestAppConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.photorex.hw2.spring.ConsoleContext.TEST_STRING;

@DisplayName("Сервис для работы с консолью")
@SpringJUnitConfig(TestAppConfig.class)
public class ConsoleServiceTest {

    private IOService console;

    @Autowired
    private ConsoleContext consoleContext;

    @BeforeEach
    void setUp() {
        console = new ConsoleService(consoleContext.getInputStream(), consoleContext.getPrintStream());
    }

    @Test
    @DisplayName("должен читать строку из InputStream")
    void shouldReturnTestStringFromInputStream() {
        assertThat(TEST_STRING).isEqualTo(console.readString());
    }

    @Test
    @DisplayName("должен записывать строку в OutputStream")
    void shouldSaveTestStringIntoOutputStream() {
        console.printString(TEST_STRING);
        assertThat(TEST_STRING + "\r\n").isEqualTo(consoleContext.getOutputStream().toString());
    }
}
