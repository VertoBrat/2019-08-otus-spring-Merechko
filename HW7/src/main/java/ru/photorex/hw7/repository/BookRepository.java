package ru.photorex.hw7.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.photorex.hw7.model.Author;
import ru.photorex.hw7.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph("BookGenre")
    List<Book> findBooksByAuthorOrderById(Author author);

    @EntityGraph("BookAuthors")
    Optional<Book> findWithAuthorsById(Long id);
}
