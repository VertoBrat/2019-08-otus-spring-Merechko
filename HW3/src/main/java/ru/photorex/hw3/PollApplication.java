package ru.photorex.hw3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.photorex.hw3.services.PollService;

@SpringBootApplication
public class PollApplication {

    public static void main(String[] args) {
       ApplicationContext context = SpringApplication.run(PollApplication.class, args);
        PollService poll = context.getBean(PollService.class);
        poll.startPollWithData();
    }

}
