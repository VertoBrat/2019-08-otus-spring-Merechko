package ru.photorex.hw5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
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
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public boolean deleteAuthor(Long id) {
        return authorRepository.delete(id);
    }
}
