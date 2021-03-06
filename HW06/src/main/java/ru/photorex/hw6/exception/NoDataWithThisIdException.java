package ru.photorex.hw6.exception;

public class NoDataWithThisIdException extends RuntimeException {

    public NoDataWithThisIdException(Long id) {
        super("There are no data with id=" + id);
    }
}
