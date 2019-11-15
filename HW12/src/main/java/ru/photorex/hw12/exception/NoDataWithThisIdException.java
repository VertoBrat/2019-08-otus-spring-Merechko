package ru.photorex.hw12.exception;

public class NoDataWithThisIdException extends RuntimeException {

    public NoDataWithThisIdException(String id) {
        super("There are no data with id =" + id);
    }
}
