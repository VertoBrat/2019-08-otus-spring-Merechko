package ru.photorex.hw14.initmongoconfig;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializationMongoDbConfig {

    @Bean
    public Mongock mongock(MongoClient mongoClient, MongoProperties properties) {
        return new SpringMongockBuilder(mongoClient, properties.getDatabase(), properties.getChangeLogPackage()).build();
    }
}
