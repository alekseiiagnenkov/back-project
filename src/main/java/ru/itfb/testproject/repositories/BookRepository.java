package ru.itfb.testproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itfb.testproject.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
