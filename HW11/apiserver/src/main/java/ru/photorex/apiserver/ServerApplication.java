package ru.photorex.apiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.photorex.apiserver.handler.AuthorHandler;
import ru.photorex.apiserver.handler.BookHandler;
import ru.photorex.apiserver.handler.GenreHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {SpringApplication.run(ServerApplication.class, args);}

    @Bean
    public RouterFunction<ServerResponse> all(BookHandler bookHandler,
                                              GenreHandler genreHandler,
                                              AuthorHandler authorHandler) {
        return route()
                .path("/books", b -> b
                    .GET("", bookHandler::all)
                    .GET("/{id}", bookHandler::byId)
                    .POST("", bookHandler::save)
                    .PUT("/{id}", bookHandler::update)
                    .DELETE("/{id}", bookHandler::delete))
                .path("/genres", b -> b
                    .GET("", genreHandler::all))
                .path("/authors", b -> b
                    .GET("", authorHandler::all))
                .build();

    }

}