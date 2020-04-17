package ru.photorex.hw12.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw12.repository.BookRepository;
import ru.photorex.hw12.to.AuthorTo;
import ru.photorex.hw12.to.mapper.AuthorMapper;
import ru.photorex.hw12.util.LibraryUtil;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormAuthorServiceImpl implements LibraryWormAuthorService {

    private final BookRepository bookRepository;
    private final AuthorMapper mapper;

    @Override
    @HystrixCommand(commandKey = "authors", fallbackMethod = "findDefaultAuthors")
    public Set<AuthorTo> findAllAuthors() {
        LibraryUtil.sleepRandomly(5, TimeUnit.SECONDS);
        return mapper.toSetTo(bookRepository.findAllAuthors());
    }

    public Set<AuthorTo> findDefaultAuthors() {
        return Collections.singleton(new AuthorTo("Default Author"));
    }
}
