package ru.photorex.hw8.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    private static final String CHANGE_LOG_PACKAGE = "ru.photorex.hw8.changelog";
    private static final String LIBRARY = "library";

    @Bean
    public Mongock mongock(MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, LIBRARY, CHANGE_LOG_PACKAGE).build();
    }
}
