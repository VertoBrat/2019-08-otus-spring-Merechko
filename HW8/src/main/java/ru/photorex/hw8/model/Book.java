package ru.photorex.hw8.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    @Field(value = "title", order = 1)
    private String title;

    @Field(value = "genres", order = 2)
    private Set<String> genres = new HashSet<>();

    @Field(value = "authors", order = 3)
    private Set<Author> authors = new HashSet<>();

    @DBRef
    @Field(value = "comments", order = 4)
    private List<Comment> comments = new ArrayList<>();

    public Book(String title, Set<String> genres, Set<Author> authors, List<Comment> comments) {
        this.title = title;
        this.genres = genres;
        this.authors = authors;
        this.comments = comments;
    }
}
