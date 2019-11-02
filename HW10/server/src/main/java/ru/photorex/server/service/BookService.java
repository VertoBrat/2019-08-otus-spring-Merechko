package ru.photorex.server.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import ru.photorex.server.model.Book;
import ru.photorex.server.to.BookTo;

public interface BookService {

    PagedModel<BookTo> getAllBook(Pageable pageable, PagedResourcesAssembler<Book> pagedResourcesAssembler);

    BookTo getBookById(String id);

    BookTo saveBook(BookTo to);

    BookTo updateBook(String id, BookTo to);

    void deleteBook(String id);

    CollectionModel<BookTo> getFilteredBook(String search, String type);
}
