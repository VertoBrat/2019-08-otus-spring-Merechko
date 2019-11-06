package ru.photorex.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class LibraryExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NoDataWithThisIdException ex) {
        ErrorResponse error = new ErrorResponse(ex);
        error.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(DataNotValidException ex) {
        ErrorResponse error = new ErrorResponse(ex);
        error.setStatus(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
