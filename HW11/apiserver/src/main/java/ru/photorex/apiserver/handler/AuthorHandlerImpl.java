package ru.photorex.apiserver.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;
import ru.photorex.apiserver.repository.BookRepository;
import ru.photorex.apiserver.to.AuthorTo;
import ru.photorex.apiserver.to.mapper.AuthorMapper;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class AuthorHandlerImpl implements AuthorHandler {

    private final Logger logger = Loggers.getLogger(AuthorHandlerImpl.class);
    private final BookRepository repository;
    private final AuthorMapper mapper;

    @Override
    public Mono<ServerResponse> all(ServerRequest request) {
        Flux<AuthorTo> authors = repository.findAllAuthors()
                .map(mapper::toTo).log(logger);
        return ok().contentType(MediaType.APPLICATION_JSON).body(authors, AuthorTo.class);
    }
}
