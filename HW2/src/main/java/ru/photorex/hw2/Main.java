package ru.photorex.hw2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.photorex.hw2.services.PollService;
import ru.photorex.hw2.services.PollServiceImpl;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        PollService poll = context.getBean(PollServiceImpl.class);
        poll.startPollWithData();
    }
}
