package ru.photorex.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import ru.photorex.server.exception.NoDataWithThisIdException;
import ru.photorex.server.model.Book;
import ru.photorex.server.repository.BookRepository;
import ru.photorex.server.to.BookTo;
import ru.photorex.server.to.assembler.BookRepresentationModelAssembler;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepresentationModelAssembler modelAssembler;
    private final BookRepository repository;

    @Override
    public PagedModel<BookTo> getAll(Pageable pageable, PagedResourcesAssembler<Book> pagedResourcesAssembler) {
        Page<Book> pagedBook = repository.findAll(pageable);
        return pagedResourcesAssembler.toModel(pagedBook, modelAssembler);
    }

    @Override
    public EntityModel<BookTo> getBookById(String id) {
        Book book = repository.findById(id).orElseThrow(() -> new NoDataWithThisIdException(id));
        return new EntityModel<>(modelAssembler.toModel(book));
    }
}
