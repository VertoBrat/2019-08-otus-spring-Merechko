package ru.photorex.hw5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.photorex.hw5.service.LibraryWormAuthorService;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LibraryApplication.class, args);
        LibraryWormAuthorService worm = context.getBean(LibraryWormAuthorService.class);
        System.out.println(worm.getAllAuthors());
    }

}
