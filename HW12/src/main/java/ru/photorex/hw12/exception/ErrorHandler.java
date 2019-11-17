package ru.photorex.hw12.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public String handle(NoDataWithThisIdException ex, Model model) {
        model.addAttribute("ex", ex);
        return "error";
    }
}
