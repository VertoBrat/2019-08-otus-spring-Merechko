package ru.photorex.hw2;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.photorex.hw2.services.ConsoleService;
import ru.photorex.hw2.services.IOService;

@Configuration
@ComponentScan
@PropertySource("application.properties")
public class AppConfig {

    @Bean
    public IOService consoleService() {
        return new ConsoleService(System.in, System.out);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
