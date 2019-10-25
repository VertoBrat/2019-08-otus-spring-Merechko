package ru.photorex.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.photorex.server.model.Book;
import ru.photorex.server.service.BookService;
import ru.photorex.server.to.BookTo;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity getAll(Pageable pageable, PagedResourcesAssembler<Book> assembler) {
        PagedModel<BookTo> pagedModel = bookService.getAll(pageable, assembler);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
}
