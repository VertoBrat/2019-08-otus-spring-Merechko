package ru.photorex.hw2.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestAppConfig {

    @Bean
    public ConsoleContext consoleContext() {
        return new ConsoleContext();
    }
}
