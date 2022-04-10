package ru.itfb.backproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Централизированное управление исключениями
 */

@ControllerAdvice
class CustomControllerAdvice {

    @ExceptionHandler(BookNotFound.class)
    public ResponseEntity<ErrorResponse> handleBookNotFoundExceptions( Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 404

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()
                ),
                status
        );
    }

    @ExceptionHandler(RoleNotFound.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundExceptions( Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 404

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()
                ),
                status
        );
    }

}
