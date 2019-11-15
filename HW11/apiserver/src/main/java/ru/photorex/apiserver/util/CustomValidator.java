package ru.photorex.apiserver.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomValidator {

    private final Validator validator;

    public <T>Mono<T> validate(T obj) {
        Objects.requireNonNull(obj);
        Set<ConstraintViolation<T>> violations = validator.validate(obj);
        if (violations == null || violations.isEmpty()) {
            return Mono.just(obj);
        }
        return Mono.error(new ConstraintViolationException(violations));
    }
}
