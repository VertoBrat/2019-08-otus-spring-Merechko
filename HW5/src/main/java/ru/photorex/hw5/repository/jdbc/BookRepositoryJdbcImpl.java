package ru.photorex.hw5.repository.jdbc;

import org.springframework.stereotype.Repository;
import ru.photorex.hw5.model.Book;
import ru.photorex.hw5.repository.BookRepository;

import java.util.List;

@Repository
public class BookRepositoryJdbcImpl implements BookRepository {
    @Override
    public Book getById(Long id) {
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
