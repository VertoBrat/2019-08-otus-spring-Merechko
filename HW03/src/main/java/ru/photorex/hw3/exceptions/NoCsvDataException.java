package ru.photorex.hw3.exceptions;

public class NoCsvDataException extends RuntimeException {

    public NoCsvDataException() {}

    public NoCsvDataException(String message) {
        super(message);
    }
}
