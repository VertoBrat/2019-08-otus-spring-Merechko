package ru.photorex.server.model;

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

    @Field(value = "genres", order = 3)
    private Set<String> genres = new HashSet<>();

    @Field(value = "authors", order = 4)
    private Set<Author> authors = new HashSet<>();

    @DBRef
    @Field(value = "comments", order = 5)
    private List<Comment> comments = new ArrayList<>();

    public Book(String title,String content, Set<String> genres, Set<Author> authors) {
        this.title = title;
        this.content = content;
        this.genres = genres;
        this.authors = authors;
    }

    public Book(String title, String content, Set<String> genres, Set<Author> authors, List<Comment> comments) {
        this(title, content, genres, authors);
        this.comments = comments;
    }

    public void addGenre(String...genre) {
        genres.addAll(Arrays.asList(genre));
    }
}
