package ru.photorex.hw9.model;

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

    @Field(value = "datetime")
    private LocalDateTime dateTime;

    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }

    public Comment(String text, Book book, LocalDateTime dateTime) {
        this(text, book);
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return id + " " + text + System.lineSeparator();
    }
}
