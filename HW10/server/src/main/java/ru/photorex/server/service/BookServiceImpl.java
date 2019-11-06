package ru.photorex.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.photorex.server.exception.NoDataWithThisIdException;
import ru.photorex.server.model.Author;
import ru.photorex.server.model.Book;
import ru.photorex.server.repository.BookRepository;
import ru.photorex.server.to.BookTo;
import ru.photorex.server.to.assembler.BookRepresentationModelAssembler;
import ru.photorex.server.to.mapper.BookMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepresentationModelAssembler modelAssembler;
    private final FilterParserService parserService;
    private final BookRepository repository;
    private final BookMapper mapper;

    @Override
    public PagedModel<BookTo> getAllBook(Pageable pageable, PagedResourcesAssembler<Book> pagedResourcesAssembler) {
        Page<Book> pagedBook = repository.findAll(pageable);
        return pagedResourcesAssembler.toModel(pagedBook, modelAssembler);
    }

    @Override
    public CollectionModel<BookTo> getFilteredBook(String search, String type) {
        if (type.equals("genre")) {
            return modelAssembler.toCollectionModel(repository.findAllFilteredPerGenre(search));
        } else {
            Author author = parserService.parseStringToAuthor(search);
            return modelAssembler.toCollectionModel(repository.findAllFilteredPerAuthors(author));
        }
    }

    @Override
    public BookTo getBookById(String id) {
        Book book = findById(id);
        return modelAssembler.toModel(book);
    }

    @Override
    @Transactional
    public BookTo saveBook(BookTo to) {
        Book book = mapper.toEntity(to);
        return modelAssembler.toModel(repository.save(book));
    }

    @Override
    @Transactional
    public BookTo updateBook(String id, BookTo to) {
        Book book = findById(id);
        Book updateDBook = mapper.updateBook(to, book);
        return modelAssembler.toModel(repository.save(updateDBook));
    }

    @Override
    @Transactional
    public void deleteBook(String id) {
        Book book = findById(id);
        repository.delete(book);
    }

    private Book findById(String id) {
        return repository.findById(id).orElseThrow(() -> new NoDataWithThisIdException(id));
    }
}
