package ru.photorex.hw5.model;

import lombok.Data;

@Data
public class Book {

    private Long id;
    private String title;
    private Genre genre;
    private Author author;
}
