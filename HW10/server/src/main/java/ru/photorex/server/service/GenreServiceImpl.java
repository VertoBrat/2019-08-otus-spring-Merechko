package ru.photorex.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.server.repository.BookRepository;
import ru.photorex.server.to.GenreTo;
import ru.photorex.server.to.mapper.GenreMapper;

import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final BookRepository bookRepository;
    private final GenreMapper mapper;

    @Override
    public Set<GenreTo> findAllGenres() {
        return mapper.toSetTo(bookRepository.findAllGenres());
    }
}
