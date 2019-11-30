package ru.photorex.hw14.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.photorex.hw14.model.sql.UserTo;

public interface UserRepository extends JpaRepository<UserTo, Long> {

    UserTo findByUserName(String userName);
}
