package ru.photorex.hw5.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import ru.photorex.hw5.repository.AuthorRepository;
import ru.photorex.hw5.repository.BookRepository;
import ru.photorex.hw5.repository.GenreRepository;
import ru.photorex.hw5.repository.jdbc.AuthorRepositoryJdbcImpl;
import ru.photorex.hw5.repository.jdbc.BookRepositoryJdbcImpl;
import ru.photorex.hw5.repository.jdbc.GenreRepositoryJdbcImpl;
import ru.photorex.hw5.repository.mapper.AuthorRowMapper;
import ru.photorex.hw5.repository.mapper.BookRowMapper;
import ru.photorex.hw5.repository.mapper.GenreRowMapper;
import ru.photorex.hw5.service.ConsoleContext;

@Configuration
public class Appconfig {

    private static final String ID = "id";
    private static final String TABLE_BOOKS = "books";
    private static final String TABLE_AUTHORS = "authors";
    private static final String TABLE_GENRE = "genres";
    private static final String SIMPLE_JDBC_INSERT_FOR_BOOK_TABLE = "simpleJdbcInsertOperationsForBookTable";
    private static final String SIMPLE_JDBC_INSERT_FOR_AUTHOR_TABLE = "simpleJdbcInsertOperationsForAuthorTable";
    private static final String SIMPLE_JDBC_INSERT_FOR_GENRE_TABLE = "simpleJdbcInsertOperationsForGenreTable";

    @Bean
    public ConsoleContext consoleContext() {
        ConsoleContext cc = new ConsoleContext();
        cc.setInputStream(System.in);
        cc.setPrintStream(System.out);
        return cc;
    }

    @Bean(SIMPLE_JDBC_INSERT_FOR_BOOK_TABLE)
    public SimpleJdbcInsertOperations jdbcInsertOperationsForBook(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_BOOKS).usingGeneratedKeyColumns(ID);
    }

    @Bean
    public BookRepository bookRepository(@Qualifier(SIMPLE_JDBC_INSERT_FOR_BOOK_TABLE) SimpleJdbcInsertOperations sip,
                                         NamedParameterJdbcOperations npo,
                                         BookRowMapper rm) {
        return new BookRepositoryJdbcImpl(npo, sip, rm);
    }

    @Bean(SIMPLE_JDBC_INSERT_FOR_AUTHOR_TABLE)
    public SimpleJdbcInsertOperations jdbcInsertOperationsForAuthor(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_AUTHORS).usingGeneratedKeyColumns(ID);
    }

    @Bean
    public AuthorRepository authorRepository(@Qualifier(SIMPLE_JDBC_INSERT_FOR_AUTHOR_TABLE) SimpleJdbcInsertOperations sip,
                                           NamedParameterJdbcOperations npo,
                                           AuthorRowMapper rm) {
        return new AuthorRepositoryJdbcImpl(npo, sip, rm);
    }

    @Bean(SIMPLE_JDBC_INSERT_FOR_GENRE_TABLE)
    public SimpleJdbcInsertOperations jdbcInsertOperationsForGenre(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_GENRE).usingGeneratedKeyColumns(ID);
    }

    @Bean
    public GenreRepository genreRepository(@Qualifier(SIMPLE_JDBC_INSERT_FOR_GENRE_TABLE) SimpleJdbcInsertOperations sip,
                                            NamedParameterJdbcOperations npo,
                                            GenreRowMapper rm) {
        return new GenreRepositoryJdbcImpl(npo, sip, rm);
    }
}
