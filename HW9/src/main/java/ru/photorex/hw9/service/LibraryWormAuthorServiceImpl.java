package ru.photorex.hw9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw9.repository.BookRepository;
import ru.photorex.hw9.to.mapper.AuthorMapper;
import ru.photorex.hw9.to.AuthorTo;

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
