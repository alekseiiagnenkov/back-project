package ru.itfb.testproject.entity.dto;

import lombok.*;
import ru.itfb.testproject.entity.Author;
import ru.itfb.testproject.entity.Book;

/**
 * Класс для передачи одновременно автора и книги
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBookDTO {

    private Author author;

    private Book book;

}
