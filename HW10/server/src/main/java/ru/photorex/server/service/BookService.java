package ru.photorex.server.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import ru.photorex.server.model.Book;
import ru.photorex.server.to.BookTo;

public interface BookService {

    PagedModel<BookTo> getAll(Pageable pageable, PagedResourcesAssembler<Book> pagedResourcesAssembler);

    EntityModel<BookTo> getBookById(String id);
}
