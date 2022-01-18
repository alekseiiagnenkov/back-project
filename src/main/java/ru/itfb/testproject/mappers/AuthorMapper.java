package ru.itfb.testproject.mappers;

import ru.itfb.testproject.entity.Author;
import ru.itfb.testproject.entity.dto.AuthorBookDTO;

public class AuthorMapper {
    public static Author dtoToEntity(AuthorBookDTO authorBookDTO){
        return new Author()
                .setFirstName(authorBookDTO.getPersonFirstName())
                .setLastName(authorBookDTO.getPersonFirstName());
    }
}
