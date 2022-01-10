package ru.itfb.testproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс для передачи одновременно автора и книги
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBookDTO {

    private Author author;

    private Book book;

}
