package ru.photorex.hw16.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public Mongock mongock(MongoClient mongoClient, MongoProperties properties) {
        return new SpringMongockBuilder(mongoClient, properties.getDatabase(), properties.getChangeLogPackage()).build();
    }
}
