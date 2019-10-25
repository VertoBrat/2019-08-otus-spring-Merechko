package ru.photorex.server.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timeStamp;

    public ErrorResponse(RuntimeException ex) {
        this.message = ex.getMessage();
        this.timeStamp = LocalDateTime.now();
    }
}
