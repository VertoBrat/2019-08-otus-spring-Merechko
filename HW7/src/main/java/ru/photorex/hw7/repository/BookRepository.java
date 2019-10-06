package ru.photorex.hw7.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.photorex.hw7.model.Author;
import ru.photorex.hw7.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph("BookGenre")
    List<Book> findBooksByAuthorOrderById(Author author);

    @EntityGraph("BookAuthors")
    Optional<Book> findWithAuthorsById(Long id);

    @EntityGraph("BookAuthors")
    List<Book> findAll(Sort sorting);
}
