package ru.photorex.hw16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.photorex.hw16.model.Book;

public interface BookRepository extends MongoRepository<Book, String>, BookCustomRepository {
}
