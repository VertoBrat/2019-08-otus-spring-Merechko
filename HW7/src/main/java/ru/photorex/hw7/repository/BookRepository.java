package ru.photorex.hw7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.photorex.hw7.model.Author;
import ru.photorex.hw7.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBooksByAuthor(Author author);
}
