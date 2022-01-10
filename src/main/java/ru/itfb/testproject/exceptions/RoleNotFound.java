package ru.itfb.testproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception если не найдена роль
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RoleNotFound extends Exception {
}
