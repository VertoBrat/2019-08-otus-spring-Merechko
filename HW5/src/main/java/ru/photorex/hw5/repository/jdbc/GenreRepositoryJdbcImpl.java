package ru.photorex.hw5.repository.jdbc;

import org.springframework.stereotype.Repository;
import ru.photorex.hw5.model.Genre;
import ru.photorex.hw5.repository.GenreRepository;

import java.util.List;

@Repository
public class GenreRepositoryJdbcImpl implements GenreRepository {
    @Override
    public Genre getById(Long id) {
        return null;
    }

    @Override
    public List<Genre> getAll() {
        return null;
    }

    @Override
    public Genre save(Genre genre) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
