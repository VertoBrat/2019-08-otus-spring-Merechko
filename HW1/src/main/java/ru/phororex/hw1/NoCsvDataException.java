package ru.phororex.hw1;

public class NoCsvDataException extends RuntimeException {

    public NoCsvDataException() {}

    public NoCsvDataException(String message) {
        super(message);
    }
}
