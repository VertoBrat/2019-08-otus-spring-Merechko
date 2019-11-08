package ru.photorex.apiserver.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface AuthorHandler {

    Mono<ServerResponse> all(ServerRequest request);
}
