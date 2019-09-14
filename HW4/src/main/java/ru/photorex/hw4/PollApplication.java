package ru.photorex.hw4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.photorex.hw4.services.PollService;

@SpringBootApplication
public class PollApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PollApplication.class, args);
        context.getBean(PollService.class).startPollWithData();
    }

}
