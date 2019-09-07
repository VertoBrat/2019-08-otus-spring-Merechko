package ru.photorex.hw2.utils;

public enum Messages {

    INPUT_NAME("family.name"),
    RESULT("result"),
    CHOOSE_LOCALE("choose.locale"),
    NONEXISTENT_LOCALE("bad.locale"),
    RIGHT_ANSWER("right.answer"),
    BAD_ANSWER("bad.answer"),
    CONNECTION_FAIL("connection.fail"),
    INVALID_DATA("invalid.data"),
    NO_DATA_IN_FILE("no.data"),
    INVALID_LANG("invalid.lang");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
