package ru.phororex.hw1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.phororex.hw1.services.PollService;
import ru.phororex.hw1.services.PollServiceImpl;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        PollService poll = context.getBean(PollServiceImpl.class);
        poll.startPollWithCSVdata();
    }
}
