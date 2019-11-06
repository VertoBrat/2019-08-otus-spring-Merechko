package ru.photorex.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.photorex.server.model.Book;

public interface BookRepository extends MongoRepository<Book, String>, BookCustomRepository {

    Page<Book> findAll(Pageable pageable);

}
