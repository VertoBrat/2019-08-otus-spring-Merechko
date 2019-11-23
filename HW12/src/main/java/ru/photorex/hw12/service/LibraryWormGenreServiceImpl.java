package ru.photorex.hw12.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw12.repository.BookRepository;
import ru.photorex.hw12.to.GenreTo;
import ru.photorex.hw12.to.mapper.GenreMapper;

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
