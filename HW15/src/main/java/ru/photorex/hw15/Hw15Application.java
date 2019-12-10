package ru.photorex.hw15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.photorex.hw15.service.Visitor;

@SpringBootApplication
// @EnableScheduling
public class Hw15Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Hw15Application.class, args);
        Visitor visitor = ctx.getBean(Visitor.class);
        visitor.makeOrder();
    }
}
