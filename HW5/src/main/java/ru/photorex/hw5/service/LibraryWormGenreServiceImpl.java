package ru.photorex.hw5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw5.model.Genre;
import ru.photorex.hw5.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormGenreServiceImpl implements LibraryWormGenreService {

    private final GenreRepository genreRepository;

    @Override
    public Genre getGenreById(Long id) {
        return genreRepository.getById(id);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.getAll();
    }

    @Override
    @Transactional
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    @Transactional
    public boolean deleteGenre(Long id) {
        return genreRepository.delete(id);
    }
}
