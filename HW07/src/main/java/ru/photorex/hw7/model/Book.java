package ru.photorex.hw7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Set;


@NamedEntityGraph(name = "BookGenre",
        attributeNodes = {
                @NamedAttributeNode("genre")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @BatchSize(size = 3)
    private Set<Author> author;

    public void removeAuthor(Author author) {
        this.author.remove(author);
    }

    public Book(Long id) {
        this.id = id;
    }

    public Book(Long id, String title, Genre genre) {
        this.id = id;
        this.title = title;
        this.genre = genre;
    }
}
