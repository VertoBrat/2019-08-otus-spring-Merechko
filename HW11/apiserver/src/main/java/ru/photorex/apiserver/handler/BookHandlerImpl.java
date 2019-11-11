package ru.photorex.apiserver.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.photorex.apiserver.model.Book;
import ru.photorex.apiserver.paging.BookPagedModel;
import ru.photorex.apiserver.repository.BookRepository;
import ru.photorex.apiserver.service.FilterParserService;
import ru.photorex.apiserver.to.BookTo;
import ru.photorex.apiserver.to.mapper.BookMapper;
import ru.photorex.apiserver.util.CustomValidator;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
@RequiredArgsConstructor
public class BookHandlerImpl implements BookHandler {

    private final FilterParserService parserService;
    private final BookRepository repository;
    private final CustomValidator validator;
    private final BookMapper mapper;
    @Value("${page.size}")
    private int pageSize;

    @Override
    public Mono<ServerResponse> all(ServerRequest request) {
        Pageable pageable = PageRequest.of(request.queryParam("page")
                .map(Integer::parseInt)
                .map(Math::abs)
                .orElse(0), pageSize);
        Mono<BookPagedModel> modelMono = repository.findAll()
                .map(mapper::toTo)
                .collectList()
                .map(list -> new BookPagedModel(list, pageable));
        return ok().contentType(MediaType.APPLICATION_JSON).body(modelMono, BookPagedModel.class);
    }

    @Override
    public Mono<ServerResponse> byId(ServerRequest request) {
        return repository.findById(request.pathVariable("id"))
                .map(mapper::toTo)
                .flatMap(b -> ok().contentType(MediaType.APPLICATION_JSON).body(fromValue(b)))
                .switchIfEmpty(notFound().build());
    }

    @Override
    public Mono<ServerResponse> filtered(ServerRequest request) {
        return request.queryParam("type").get().equals("genre") ? filteredByGenre(request) : filteredByAuthor(request);
    }

    @Override
    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(BookTo.class)
                .flatMap(validator::validate)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toTo)
                .flatMap(b -> created(request.uriBuilder().pathSegment(b.getId()).build())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(b)));
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

    private Mono<ServerResponse> filteredByAuthor(ServerRequest request) {
        return request.queryParam("search")
                .map(parserService::parseStringToAuthor)
                .map(repository::findAllFilteredPerAuthors)
                .map(f -> f.map(mapper::toTo))
                .map(books -> ok().contentType(MediaType.APPLICATION_JSON).body(books, BookTo.class))
                .orElse(noContent().build());
    }

    private Mono<ServerResponse> filteredByGenre(ServerRequest request) {
        return request.queryParam("search")
                .map(repository::findAllFilteredPerGenre)
                .map(f -> f.map(mapper::toTo))
                .map(books -> ok().contentType(MediaType.APPLICATION_JSON).body(books, BookTo.class))
                .orElse(noContent().build());
    }
}
