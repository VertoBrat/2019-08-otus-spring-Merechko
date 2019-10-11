package ru.photorex.hw7.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw7.exception.NoDataWithThisIdException;
import ru.photorex.hw7.model.Genre;
import ru.photorex.hw7.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormGenreServiceImpl implements LibraryWormGenreService {

    private final GenreRepository genreRepository;

    @Override
    public Genre getGenreById(Long id) {
        return genreRepository.findById(id).orElseThrow(() -> new NoDataWithThisIdException(id));
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    @Transactional
    public Genre updateGenre(Genre genre) {
        Genre genreDb = genreRepository.findById(genre.getId()).orElseThrow(() -> new NoDataWithThisIdException(genre.getId()));
        genreDb.setName(genre.getName());
        return genreDb;
    }

    @Override
    @Transactional
    public void deleteGenreById(Long id) {
        genreRepository.deleteById(id);
    }
}
