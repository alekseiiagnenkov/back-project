package ru.itfb.testproject.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.itfb.testproject.entity.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
