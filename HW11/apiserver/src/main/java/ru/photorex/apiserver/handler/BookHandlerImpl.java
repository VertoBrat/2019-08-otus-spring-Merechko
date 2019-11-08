package ru.photorex.apiserver.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.photorex.apiserver.repository.BookRepository;
import ru.photorex.apiserver.to.mapper.BookMapper;
import ru.photorex.apiserver.util.CustomValidator;

@Component
@RequiredArgsConstructor
public class BookHandlerImpl implements BookHandler {

    private final BookRepository repository;
    private final CustomValidator validator;
    private final BookMapper mapper;

    @Override
    public Mono<ServerResponse> all(ServerRequest request) {
        return null;
    }

    @Override
    public Mono<ServerResponse> byId(ServerRequest request) {
        return null;
    }

    @Override
    public Mono<ServerResponse> save(ServerRequest request) {
        return null;
    }

    @Override
    public Mono<ServerResponse> update(ServerRequest request) {
        return null;
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest request) {
        return null;
    }
}
