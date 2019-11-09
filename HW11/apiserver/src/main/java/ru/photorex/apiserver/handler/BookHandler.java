package ru.photorex.apiserver.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface BookHandler {

    Mono<ServerResponse> all(ServerRequest request);

    Mono<ServerResponse> byId(ServerRequest request);

    Mono<ServerResponse> filtered(ServerRequest request);

    Mono<ServerResponse> save(ServerRequest request);

    Mono<ServerResponse> update(ServerRequest request);

    Mono<ServerResponse> delete(ServerRequest request);
}
