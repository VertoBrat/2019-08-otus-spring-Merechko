package ru.photorex.hw6.repository;

import org.springframework.stereotype.Repository;
import ru.photorex.hw6.model.Author;

import java.util.List;

@Repository
public class AuthorRepositoryJpaImpl implements AuthorRepository {
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
