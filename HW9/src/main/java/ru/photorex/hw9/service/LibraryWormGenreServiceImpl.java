package ru.photorex.hw9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw9.exception.NoDataWithThisIdException;
import ru.photorex.hw9.model.Book;
import ru.photorex.hw9.repository.BookRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormGenreServiceImpl implements LibraryWormGenreService {

    private final BookRepository bookRepository;

    @Override
    public Set<String> findAllGenres() {
        return bookRepository.findAllGenres();
    }

    @Override
    @Transactional
    public void saveGenre(String bookId, String genre) {
        Book book = findBook(bookId);
        book.getGenres().add(genre);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteGenre(String bookId, String genre) {
        Book book = findBook(bookId);
        Set<String> genres = book.getGenres().stream().filter(g -> !g.equals(genre)).collect(Collectors.toSet());
        book.setGenres(genres);
        bookRepository.save(book);
    }

    private Book findBook(String bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new NoDataWithThisIdException(bookId));
    }
}
