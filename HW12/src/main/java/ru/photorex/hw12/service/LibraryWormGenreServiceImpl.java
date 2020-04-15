package ru.photorex.hw12.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw12.repository.BookRepository;
import ru.photorex.hw12.to.GenreTo;
import ru.photorex.hw12.to.mapper.GenreMapper;
import ru.photorex.hw12.util.LibraryUtil;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormGenreServiceImpl implements LibraryWormGenreService {

    private final BookRepository bookRepository;
    private final GenreMapper mapper;

    @Override
    @HystrixCommand(fallbackMethod = "findDefaultGenre", commandKey = "genres")
    public Set<GenreTo> findAllGenres() {
        LibraryUtil.sleepRandomly(4, TimeUnit.SECONDS);
        return mapper.toSetTo(bookRepository.findAllGenres());
    }

    public Set<GenreTo> findDefaultGenre() {
        return Collections.singleton(new GenreTo("Default"));
    }
}
