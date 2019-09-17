package ru.photorex.hw4.services;

import ru.photorex.hw4.model.Result;

import java.util.List;

public interface PollMaxResultService {

    List<Result> getMaxResultList();

    boolean addResult(String userName, int result);
}
