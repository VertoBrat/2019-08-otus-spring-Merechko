package ru.photorex.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.server.repository.BookRepository;
import ru.photorex.server.to.AuthorTo;
import ru.photorex.server.to.mapper.AuthorMapper;

import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final BookRepository bookRepository;
    private final AuthorMapper mapper;

    @Override
    public Set<AuthorTo> findAllAuthors() {
        return mapper.toSetTo(bookRepository.findAllAuthors());
    }
}
