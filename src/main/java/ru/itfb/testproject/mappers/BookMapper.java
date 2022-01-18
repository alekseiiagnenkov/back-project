package ru.itfb.testproject.mappers;

import ru.itfb.testproject.entity.Book;
import ru.itfb.testproject.entity.dto.AuthorBookDTO;

public class BookMapper {
    public static Book dtoToEntity(AuthorBookDTO authorBookDTO){
        return new Book()
                .setName(authorBookDTO.getBookName());
    }
}
