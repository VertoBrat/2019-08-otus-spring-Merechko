package ru.photorex.hw4.exceptions;

public class NoCsvDataException extends RuntimeException {

    public NoCsvDataException() {}

    public NoCsvDataException(String message) {
        super(message);
    }
}
