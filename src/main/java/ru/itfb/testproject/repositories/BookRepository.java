package ru.itfb.testproject.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.itfb.testproject.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
