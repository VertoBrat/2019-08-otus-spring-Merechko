package ru.photorex.server.exception;

import java.util.List;

public class DataNotValidException extends RuntimeException {
    public DataNotValidException(List<String> errorFields) {
        super("Can't save your data, errors in fields: " + errorFields);
    }
}
