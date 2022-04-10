package ru.itfb.backproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.backproject.entity.Author;

/**
 * Репозиторий для {@link Author}
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
