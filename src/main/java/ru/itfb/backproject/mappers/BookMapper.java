package ru.itfb.backproject.mappers;

import ru.itfb.backproject.entity.Book;
import ru.itfb.backproject.entity.dto.AuthorBookDTO;

public class BookMapper {
    public static Book dtoToEntity(AuthorBookDTO authorBookDTO){
        return new Book()
                .setName(authorBookDTO.getBookName());
    }
}
