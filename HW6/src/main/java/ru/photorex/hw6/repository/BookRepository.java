package ru.photorex.hw6.repository;

import ru.photorex.hw6.model.Author;
import ru.photorex.hw6.model.Book;

import java.util.List;

public interface BookRepository {

    Book getById(Long id);

    List<Book> getByAuthor(Author author);

    List<Book> getAll();

    Book save(Book book);

    boolean delete(Long id);
}
