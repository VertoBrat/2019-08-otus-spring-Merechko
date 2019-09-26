package ru.photorex.hw6.repository;

import org.springframework.stereotype.Repository;
import ru.photorex.hw6.model.Genre;

import java.util.List;

@Repository
public class GenreRepositoryJpaImpl implements GenreRepository {
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
