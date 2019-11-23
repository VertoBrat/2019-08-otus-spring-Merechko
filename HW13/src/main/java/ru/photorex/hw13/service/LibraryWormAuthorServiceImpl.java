package ru.photorex.hw13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw13.repository.BookRepository;
import ru.photorex.hw13.to.AuthorTo;
import ru.photorex.hw13.to.mapper.AuthorMapper;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormAuthorServiceImpl implements LibraryWormAuthorService {

    private final BookRepository bookRepository;
    private final AuthorMapper mapper;

    @Override
    public Set<AuthorTo> findAllAuthors() {
        return mapper.toSetTo(bookRepository.findAllAuthors());
    }
}
