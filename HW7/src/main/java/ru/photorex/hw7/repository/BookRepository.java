package ru.photorex.hw7.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.photorex.hw7.model.Author;
import ru.photorex.hw7.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBooksByAuthor(Author author);

    @EntityGraph("BookAuthors")
    Book findWithAuthorsById(Long id);

    @Query("select b from Book b where b.id=:id")
    Optional<Book> findWithoutGenre(@Param("id") Long id);

    @Modifying
    @Query(value = "delete from books_authors ba where ba.author_id=:authorId and ba.book_id=:bookId",
            nativeQuery = true)
    void deleteAuthor(@Param("authorId") Long authorId, @Param("bookId") Long bookId);
}
