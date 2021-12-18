package ru.itfb.testproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.testproject.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
