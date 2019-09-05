package ru.photorex.hw2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.photorex.hw2.services.ConsoleService;
import ru.photorex.hw2.services.IOService;

@Configuration
@ComponentScan
public class AppConfig {

    @Bean
    public IOService consoleService() {
        return new ConsoleService(System.in, System.out);
    }
}
