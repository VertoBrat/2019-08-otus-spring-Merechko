package ru.photorex.hw14.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.photorex.hw14.model.sql.RoleTo;

public interface RoleRepository extends JpaRepository<RoleTo, Long> {
}
