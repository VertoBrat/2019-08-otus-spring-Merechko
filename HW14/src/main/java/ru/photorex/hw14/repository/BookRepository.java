package ru.photorex.hw14.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.photorex.hw14.model.sql.BookTo;

public interface BookRepository extends JpaRepository<BookTo, Long> {

    BookTo findByIsbn(String isbn);
}
