package ru.photorex.hw8.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.photorex.hw8.service.ConsoleContext;

@Configuration
public class ApplicationConfig {

    @Bean
    public Mongock mongock(MongoClient mongoClient, MongoProperties properties) {
        return new SpringMongockBuilder(mongoClient, properties.getDatabase(), properties.getChangeLogPackage()).build();
    }

    @Bean
    public ConsoleContext consoleContext() {
        ConsoleContext cc = new ConsoleContext();
        cc.setInputStream(System.in);
        cc.setPrintStream(System.out);
        return cc;
    }
}
