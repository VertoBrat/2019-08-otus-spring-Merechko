package ru.photorex.hw2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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

    @Bean
    public MessageSource messageSource(@Value("${bundlePath}") String bundlePath,
                                       @Value("${bundleEncoding}") String encoding) {
        ReloadableResourceBundleMessageSource ms =
                new ReloadableResourceBundleMessageSource();
        ms.setBasename(bundlePath);
        ms.setDefaultEncoding(encoding);
        return ms;
    }
}
