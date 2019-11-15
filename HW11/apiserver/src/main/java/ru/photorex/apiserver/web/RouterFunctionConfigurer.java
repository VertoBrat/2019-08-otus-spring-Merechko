package ru.photorex.apiserver.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.photorex.apiserver.handler.AuthorHandler;
import ru.photorex.apiserver.handler.BookHandler;
import ru.photorex.apiserver.handler.CommentHandler;
import ru.photorex.apiserver.handler.GenreHandler;

import java.util.function.Predicate;

import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfigurer {

    @Bean
    public RouterFunction<ServerResponse> all(BookHandler bookHandler,
                                              GenreHandler genreHandler,
                                              AuthorHandler authorHandler,
                                              CommentHandler commentHandler) {
        return route()
                .path("/books", b -> b
                        .GET("", queryParam("page", Predicate.not(String::isEmpty)), bookHandler::all)
                        .GET("", queryParam("search", Predicate.not(String::isEmpty))
                                        .and(queryParam("type", Predicate.not(String::isEmpty))),
                                bookHandler::filtered)
                        .GET("/{id}", bookHandler::byId)
                        .POST("", bookHandler::save)
                        .PUT("/{id}", bookHandler::update)
                        .DELETE("/{id}", bookHandler::delete))
                .path("/comments", b -> b
                        .PUT("/{id}", commentHandler::update)
                        .POST("", commentHandler::save)
                        .DELETE("/{id}", commentHandler::delete))
                .path("/genres", b -> b
                        .GET("", genreHandler::all))
                .path("/authors", b -> b
                        .GET("", authorHandler::all))
                .build();
    }
}
