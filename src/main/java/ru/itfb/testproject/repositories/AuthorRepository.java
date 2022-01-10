package ru.itfb.testproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.testproject.model.Author;

/**
 * Репозиторий для {@link Author}
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
