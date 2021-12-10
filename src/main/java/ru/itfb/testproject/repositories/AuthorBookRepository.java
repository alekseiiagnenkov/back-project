package ru.itfb.testproject.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.itfb.testproject.entities.AuthorBook;

public interface AuthorBookRepository extends CrudRepository<AuthorBook, Long> {
}
