package ru.photorex.apiserver.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.photorex.apiserver.model.Book;
import ru.photorex.apiserver.repository.BookRepository;
import ru.photorex.apiserver.to.BookTo;
import ru.photorex.apiserver.to.mapper.BookMapper;
import ru.photorex.apiserver.util.CustomValidator;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
@RequiredArgsConstructor
public class BookHandlerImpl implements BookHandler {

    private final BookRepository repository;
    private final CustomValidator validator;
    private final BookMapper mapper;

    @Override
    public Mono<ServerResponse> all(ServerRequest request) {
        Flux<BookTo> books = repository.findAll().map(mapper::toTo);
        return ok().contentType(MediaType.APPLICATION_JSON).body(books, BookTo.class);
    }

    @Override
    public Mono<ServerResponse> byId(ServerRequest request) {
        return repository.findById(request.pathVariable("id"))
                .map(mapper::toTo)
                .flatMap(b -> ok().contentType(MediaType.APPLICATION_JSON).body(fromValue(b)))
                .switchIfEmpty(notFound().build());
    }

    @Override
    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<BookTo> to = request.bodyToMono(BookTo.class)
                .flatMap(validator::validate)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toTo);
        return ok().contentType(MediaType.APPLICATION_JSON).body(to, BookTo.class);
    }

    @Override
    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<BookTo> to = request.bodyToMono(BookTo.class)
                .flatMap(validator::validate)
                .transform(toBook -> {
                    Mono<Book> dbBook = repository.findById(request.pathVariable("id"));
                    return Mono.zip(toBook, dbBook, mapper::updateBook);
                })
                .flatMap(repository::save)
                .map(mapper::toTo);
        return ok().contentType(MediaType.APPLICATION_JSON).body(to, BookTo.class);
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest request) {
        return noContent().build(repository.deleteById(request.pathVariable("id")));
    }
}
