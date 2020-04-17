package ru.photorex.hw7.exception;

public class NoDataWithThisIdException extends RuntimeException {

    public NoDataWithThisIdException(Long id) {
        super("There are no data for id=" + id);
    }
}
