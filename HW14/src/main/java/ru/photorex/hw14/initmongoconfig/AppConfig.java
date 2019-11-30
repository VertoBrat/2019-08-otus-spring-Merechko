package ru.photorex.hw14.initmongoconfig;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.photorex.hw14.service.ConsoleContext;

@Configuration
public class AppConfig {

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
