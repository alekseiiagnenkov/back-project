package ru.itfb.testproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception если не найдена книга
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BookNotFound extends Exception {

    public BookNotFound(){
        super();
    }

    public BookNotFound(String message){
        super(message);
    }
}
