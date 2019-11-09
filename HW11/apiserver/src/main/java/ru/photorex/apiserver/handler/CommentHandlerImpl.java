package ru.photorex.apiserver.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.photorex.apiserver.model.Comment;
import ru.photorex.apiserver.repository.CommentRepository;
import ru.photorex.apiserver.to.CommentTo;
import ru.photorex.apiserver.to.mapper.CommentMapper;
import ru.photorex.apiserver.util.CustomValidator;

import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class CommentHandlerImpl implements CommentHandler {

    private final CommentRepository repository;
    private final CustomValidator validator;
    private final CommentMapper mapper;

    @Override
    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<CommentTo> comment = request.bodyToMono(CommentTo.class)
                .flatMap(validator::validate)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toTo);
        return ok().contentType(MediaType.APPLICATION_JSON).body(comment, CommentTo.class);
    }

    @Override
    public Mono<ServerResponse> update(ServerRequest request) {
        Mono<CommentTo> to = request.bodyToMono(CommentTo.class)
                .flatMap(validator::validate)
                .transform(toComment -> {
                    Mono<Comment> dbComment = repository.findById(request.pathVariable("id"));
                    return Mono.zip(toComment, dbComment, mapper::updateComment);
                })
                .flatMap(repository::save)
                .map(mapper::toTo);
        return ok().contentType(MediaType.APPLICATION_JSON).body(to, CommentTo.class);
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest request) {
        return noContent().build(repository.deleteById(request.pathVariable("id")));
    }
}
