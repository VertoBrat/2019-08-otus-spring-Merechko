package ru.photorex.hw12.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.photorex.hw12.model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUserName(String userName);
}
