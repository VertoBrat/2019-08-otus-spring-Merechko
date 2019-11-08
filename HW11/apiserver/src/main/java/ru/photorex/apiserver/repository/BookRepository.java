package ru.photorex.apiserver.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.photorex.apiserver.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookCustomRepository {

}
