package ru.photorex.hw2.services;

import org.junit.jupiter.api.Test;
import ru.photorex.hw2.services.utils.ConsoleServiceHelper;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ConsoleServiceTest {

    private static final String TEST_STRING = "test string";
    private ConsoleService cs = ConsoleServiceHelper.getInstance(TEST_STRING);

    @Test
    void readString() {
        assertThat(TEST_STRING).isEqualTo(cs.readString());
    }

    @Test
    void printString() {
        cs.printString(TEST_STRING);
        assertThat(TEST_STRING  + "\r\n").isEqualTo(ConsoleServiceHelper.output.toString());
    }
}
