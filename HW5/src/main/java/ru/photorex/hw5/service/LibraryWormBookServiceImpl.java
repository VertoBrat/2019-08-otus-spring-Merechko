package ru.photorex.hw5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.photorex.hw5.model.Author;
import ru.photorex.hw5.model.Book;
import ru.photorex.hw5.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryWormBookServiceImpl implements LibraryWormBookService {

    private final BookRepository bookRepository;

    @Override
    public Book getBookById(Long id) {
        return bookRepository.getById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        return bookRepository.getByAuthor(author);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public boolean deleteBook(Long id) {
        return bookRepository.delete(id);
    }
}
