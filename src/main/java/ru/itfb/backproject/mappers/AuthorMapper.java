package ru.itfb.backproject.mappers;

import ru.itfb.backproject.entity.Author;
import ru.itfb.backproject.entity.dto.AuthorBookDTO;

public class AuthorMapper {
    public static Author dtoToEntity(AuthorBookDTO authorBookDTO){
        return new Author()
                .setFirstName(authorBookDTO.getPersonFirstName())
                .setLastName(authorBookDTO.getPersonFirstName());
    }
}
