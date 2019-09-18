package ru.photorex.hw5.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Author {

    private Long id;
    private String firstName;
    private String lastName;
    private List<Book> books = new ArrayList<>();
}
