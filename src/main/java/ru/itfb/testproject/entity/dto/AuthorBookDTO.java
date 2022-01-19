package ru.itfb.testproject.entity.dto;

import lombok.*;

/**
 * Класс для передачи одновременно автора и книги
 */
@Getter
@NoArgsConstructor
public class AuthorBookDTO {

    //Person
    private String personFirstName;

    private String personLastName;

    //Book
    private String bookName;

    public AuthorBookDTO setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
        return this;
    }

    public AuthorBookDTO setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
        return this;
    }

    public AuthorBookDTO setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }
}
