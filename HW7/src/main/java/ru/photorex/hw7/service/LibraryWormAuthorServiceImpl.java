package ru.photorex.hw7.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw7.exception.NoDataWithThisIdException;
import ru.photorex.hw7.model.Author;
import ru.photorex.hw7.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormAuthorServiceImpl implements LibraryWormAuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new NoDataWithThisIdException(id));
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) {;
        authorRepository.deleteById(id);
    }
}
