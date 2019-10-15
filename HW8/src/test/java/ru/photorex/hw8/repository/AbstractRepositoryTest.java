package ru.photorex.hw8.repository;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.photorex.hw8.config", "ru.photorex.hw8.repository", "ru.photorex.hw8.events"})
public class AbstractRepositoryTest {
}
