package ru.photorex.hw9.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.photorex.hw9.model.Author;
import ru.photorex.hw9.model.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookCustomRepository {

    List<Book> findAllByAuthors(Author author);

    List<Book> findAllByGenres(String genre);
}
