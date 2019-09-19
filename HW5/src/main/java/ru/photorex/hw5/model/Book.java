package ru.photorex.hw5.model;

import lombok.Data;

import java.util.Set;

@Data
public class Book {

    private Long id;
    private String title;
    private Genre genre;
    private Set<Author> author;
}
