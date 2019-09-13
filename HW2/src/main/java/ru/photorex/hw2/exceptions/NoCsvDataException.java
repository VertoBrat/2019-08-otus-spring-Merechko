package ru.photorex.hw2.exceptions;

public class NoCsvDataException extends RuntimeException {

    public NoCsvDataException() {}

    public NoCsvDataException(String message) {
        super(message);
    }
}
