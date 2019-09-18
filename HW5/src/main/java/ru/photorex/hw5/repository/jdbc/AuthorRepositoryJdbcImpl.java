package ru.photorex.hw5.repository.jdbc;

import org.springframework.stereotype.Repository;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.repository.AuthorRepository;

import java.util.List;

@Repository
public class AuthorRepositoryJdbcImpl implements AuthorRepository {
    @Override
    public Author getById(Long id) {
        return null;
    }

    @Override
    public List<Author> getAll() {
        return null;
    }

    @Override
    public Author save(Author author) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
