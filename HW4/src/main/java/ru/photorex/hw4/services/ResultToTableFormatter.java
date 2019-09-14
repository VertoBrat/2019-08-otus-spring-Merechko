package ru.photorex.hw4.services;

import ru.photorex.hw4.model.Result;

import java.util.List;

public interface ResultToTableFormatter {

    void format(List<Result> results);
}
