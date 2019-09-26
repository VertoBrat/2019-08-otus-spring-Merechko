package ru.photorex.hw6.repository;

import org.springframework.stereotype.Repository;
import ru.photorex.hw6.model.Author;
import ru.photorex.hw6.model.Book;

import java.util.List;

@Repository
public class BookRepositoryJpaImpl implements BookRepository {
    @Override
    public Book getById(Long id) {
        return null;
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public Book save(Book book) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
