package ru.photorex.hw13.exception;

public class NoDataWithThisIdException extends RuntimeException {

    public NoDataWithThisIdException(String id) {
        super("Нет данных с id =" + id);
    }
}
