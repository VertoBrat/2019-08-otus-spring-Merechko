package ru.photorex.hw9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw9.repository.BookRepository;
import ru.photorex.hw9.to.mapper.GenreMapper;
import ru.photorex.hw9.to.GenreTo;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormGenreServiceImpl implements LibraryWormGenreService {

    private final BookRepository bookRepository;
    private final GenreMapper mapper;

    @Override
    public Set<GenreTo> findAllGenres() {
        return mapper.toSetTo(bookRepository.findAllGenres());
    }
}
