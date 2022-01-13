package ru.itfb.testproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.testproject.entity.AuthorBook;

/**
 * Репозиторий для {@link AuthorBook}
 */
@Repository
public interface AuthorBookRepository extends JpaRepository<AuthorBook, Long> {
}
