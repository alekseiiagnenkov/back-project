package ru.itfb.testproject.entity.dto;

import lombok.*;

/**
 * Класс для передачи одновременно автора и книги
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBookDTO {

    //Person
    private String personFirstName;

    private String personLastName;

    //Book
    private String bookName;

}
