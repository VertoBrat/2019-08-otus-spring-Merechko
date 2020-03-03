package ru.photorex.server.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.photorex.server.model.Book;
import ru.photorex.server.service.BookService;
import ru.photorex.server.to.BookTo;

import javax.validation.Valid;

import java.net.URI;

import static ru.photorex.server.utils.DataValidation.checkErrors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @GetMapping("/books")
    public ResponseEntity getAll(Pageable pageable, PagedResourcesAssembler<Book> assembler) {
        logger.info("getAll with page {}", pageable.getPageNumber());
        PagedModel<BookTo> pagedModel = bookService.getAllBook(pageable, assembler);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping(value = "/books", params = {"search", "type"})
    public ResponseEntity getAllFiltered(@RequestParam("search") String search,
                                         @RequestParam("type") String type) {
        logger.info("getAllFiltered with search {}, type {}", search, type);
        CollectionModel<BookTo> filteredBooks = bookService.getFilteredBook(search, type);
        if (filteredBooks.getContent().isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(filteredBooks);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity getById(@PathVariable("id") String id) {
        logger.info("getById with id {}", id);
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping("/books")
    public ResponseEntity save(@Valid @RequestBody BookTo to, BindingResult result) {
        logger.info("save with {}, count of error {}", to, result.getErrorCount());
        checkErrors(result);
        BookTo bookTo = bookService.saveBook(to);
        URI location = bookTo.getLink("self").get().toUri();
        return ResponseEntity.created(location).body(bookTo);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity update(@PathVariable("id") String id,@Valid @RequestBody BookTo to, BindingResult result) {
        logger.info("update with id {}, entity {}, count of error {}", id, to, result.getErrorCount());
        checkErrors(result);
        return ResponseEntity.ok(bookService.updateBook(id, to));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        logger.info("delete with id {}", id);
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
