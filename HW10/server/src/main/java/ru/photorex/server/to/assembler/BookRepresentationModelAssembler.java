package ru.photorex.server.to.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import ru.photorex.server.controller.BookController;
import ru.photorex.server.model.Book;
import ru.photorex.server.to.BookTo;
import ru.photorex.server.to.mapper.BookMapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookRepresentationModelAssembler extends RepresentationModelAssemblerSupport<Book, BookTo> {

    private final BookMapper mapper;

    public BookRepresentationModelAssembler(BookMapper mapper) {
        super(BookController.class, BookTo.class);
        this.mapper = mapper;
    }

    @Override
    public BookTo toModel(Book entity) {
        BookTo to = mapper.toTo(entity);
        to.add(linkTo(methodOn(BookController.class).getById(to.getId())).withSelfRel());
        return to;
    }
}
