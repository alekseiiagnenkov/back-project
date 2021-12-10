package ru.itfb.testproject.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.itfb.testproject.models.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
