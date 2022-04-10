package ru.itfb.backproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.backproject.entity.Book;

/**
 * Репозиторий для {@link Book}
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
