package ru.photorex.hw12.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.hw12.exception.NoDataWithThisIdException;
import ru.photorex.hw12.model.Author;
import ru.photorex.hw12.model.Book;
import ru.photorex.hw12.repository.BookRepository;
import ru.photorex.hw12.to.BookTo;
import ru.photorex.hw12.to.CommentTo;
import ru.photorex.hw12.to.Filter;
import ru.photorex.hw12.to.mapper.BookMapper;
import ru.photorex.hw12.util.LibraryUtil;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryWormBookServiceImpl implements LibraryWormBookService {

    private final BookRepository bookRepository;
    private final FilterParserService parserService;
    private final BookMapper mapper;

    @Override
    @HystrixCommand(fallbackMethod = "findDefaultBooks", commandKey = "books")
    public List<BookTo> findAllBooks() {
        LibraryUtil.sleepRandomly(2, TimeUnit.SECONDS);
        return mapper.toListTo(bookRepository.findAll());
    }

    public List<BookTo> findDefaultBooks() {
        BookTo to = new BookTo();
        to.setTitle("Default");
        to.setComments(Collections.singletonList(new CommentTo()));
        return Collections.singletonList(to);
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
