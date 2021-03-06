package ru.itfb.backproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.backproject.entity.Person;

/**
 * Репозиторий для {@link Person}
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findUserByUsername(String username);
}
