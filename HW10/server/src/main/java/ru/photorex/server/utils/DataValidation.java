package ru.photorex.server.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import ru.photorex.server.exception.DataNotValidException;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataValidation {

    public static void checkErrors(BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorFields =
                    result.getFieldErrors().stream()
                            .map(r -> r.getField() + " " + r.getDefaultMessage())
                            .collect(Collectors.toList());
            throw new DataNotValidException(errorFields);
        }
    }
}
