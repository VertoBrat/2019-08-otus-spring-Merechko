package ru.photorex.hw3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.photorex.hw3.services.ConsoleContext;

@Configuration
public class AppConfig {

    @Bean
    public ConsoleContext consoleContext() {
        ConsoleContext cc = new ConsoleContext();
        cc.setInputStream(System.in);
        cc.setPrintStream(System.out);
        return cc;
    }
}
