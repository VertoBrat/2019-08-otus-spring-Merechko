package ru.photorex.hw13.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    @Field(value = "commentText")
    private String text;

    @DBRef
    private Book book;

    @DBRef
    private User user;

    @Field(value = "datetime")
    private LocalDateTime dateTime;

    public Comment(String text, Book book, User user) {
        this.text = text;
        this.book = book;
        this.user = user;
    }

    public Comment(String text, Book book, User user, LocalDateTime dateTime) {
        this(text, book, user);
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return id + " " + text + System.lineSeparator();
    }
}
