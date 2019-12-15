package ru.photorex.hw15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Hw15Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw15Application.class, args);
    }
}
