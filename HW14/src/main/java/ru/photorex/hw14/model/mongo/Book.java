package ru.photorex.hw14.model.mongo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.*;

@Data
@NoArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    @Field(value = "title", order = 1)
    private String title;

    @Field(value = "content", order = 2)
    private String content;

    @Field(value = "genre", order = 3)
    private String genre;

    @Field(value = "ISBN", order = 4)
    private String isbn;

    @Field(value = "authors", order = 5)
    private Set<Author> authors = new HashSet<>();

    @DBRef
    @Field(value = "comments", order = 6)
    private List<Comment> comments = new ArrayList<>();

    public Book(String title,String content, String genre,String isbn, Set<Author> authors) {
        this.title = title;
        this.content = content;
        this.genre = genre;
        this.isbn = isbn;
        this.authors = authors;
    }

    public Book(String title, String content, String genre,String isbn, Set<Author> authors, List<Comment> comments) {
        this(title, content, genre,isbn, authors);
        this.comments = comments;
    }
}
