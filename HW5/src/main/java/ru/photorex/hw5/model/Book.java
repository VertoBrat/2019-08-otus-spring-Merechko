package ru.photorex.hw5.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Long id;
    private String title;
    private Genre genre;
    private List<Author> author;

    public Book(Long id, String title, Genre genre) {
        this.id = id;
        this.title = title;
        this.genre = genre;
    }
}
