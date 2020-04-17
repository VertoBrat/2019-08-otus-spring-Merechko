package ru.photorex.hw6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw6.model.Author;
import ru.photorex.hw6.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormAuthorServiceImpl implements LibraryWormAuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.getById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.getAll();
    }

    @Override
    @Transactional
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public boolean deleteAuthor(Long id) {
        return authorRepository.delete(id);
    }
}
