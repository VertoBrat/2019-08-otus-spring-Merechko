package ru.photorex.hw2.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.photorex.hw2.services.ConsoleContext;
import ru.photorex.hw2.services.ConsoleService;
import ru.photorex.hw2.services.IOService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

@Configuration
public class TestAppConfig {

    public static final String TEST_STRING = "test string";

    @Bean
    public ConsoleContext consoleContext() {
        InputStream in = new ByteArrayInputStream(TEST_STRING.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);
        ConsoleContext cc = new ConsoleContext();
        cc.setPrintStream(out);
        cc.setInputStream(in);
        cc.setOutputStream(outputStream);
        return cc;
    }

    @Bean
    public IOService consoleService(ConsoleContext context) {
        return new ConsoleService(context);
    }
}
