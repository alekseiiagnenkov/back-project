package ru.itfb.testproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itfb.testproject.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
