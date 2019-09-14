package ru.photorex.hw4.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(exclude = {"maxResult"})
public class Result implements Comparable<Result> {

    private final String userName;
    private final Integer maxResult;

    @Override
    public int compareTo(Result o) {
        return o.getMaxResult().compareTo(this.getMaxResult());
    }
}
