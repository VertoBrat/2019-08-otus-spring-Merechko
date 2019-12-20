package ru.photorex.hw16.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw16.exception.NoDataWithThisIdException;
import ru.photorex.hw16.model.Author;
import ru.photorex.hw16.model.Book;
import ru.photorex.hw16.repository.BookRepository;
import ru.photorex.hw16.to.BookTo;
import ru.photorex.hw16.to.Filter;
import ru.photorex.hw16.to.mapper.BookMapper;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormBookServiceImpl implements LibraryWormBookService {

    private final BookRepository bookRepository;
    private final FilterParserService parserService;
    private final BookMapper mapper;

    @Override
    public List<BookTo> findAllBooks() {
        return mapper.toListTo(bookRepository.findAll());
    }

    @Override
    public BookTo findBookById(String id) {
        Book book = findById(id);
        return mapper.toTo(book);
    }

    @Override
    @Transactional
    public BookTo updateSaveBook(BookTo to) {
        if (!to.getId().isEmpty()) {
            Book book = findById(to.getId());
            return mapper.toTo(bookRepository.save(mapper.updateBook(to, book)));
        }
        Book book = mapper.toEntity(to);
        return mapper.toTo(bookRepository.save(book));
    }

    @Override
    public List<BookTo> filteredBooks(Filter filter) {
        if (filter.getType().equals("genre"))
            return mapper.toListTo(bookRepository.findAllFilteredPerGenre(filter.getFilterText()));
        else {
            Author author = parserService.parseStringToAuthor(filter.getFilterText());
            return mapper.toListTo(bookRepository.findAllFilteredPerAuthors(author));
        }
    }

    @Override
    @Transactional
    public void deleteBook(String id) {
        Book book = findById(id);
        bookRepository.delete(book);
    }

    private Book findById(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new NoDataWithThisIdException(id));
    }
}
