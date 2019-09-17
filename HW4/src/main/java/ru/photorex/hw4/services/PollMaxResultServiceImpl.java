package ru.photorex.hw4.services;

import org.springframework.stereotype.Service;
import ru.photorex.hw4.model.Result;

import java.util.ArrayList;
import java.util.List;

@Service
public class PollMaxResultServiceImpl implements PollMaxResultService {

    private static final String USER1 = "Dima";
    private static final String USER2 = "Goga";
    private static final String USER3 = "Igor";
    private static final int MAX_RESULT_USER1 = 3;
    private static final int MAX_RESULT_USER2 = 0;
    private static final int MAX_RESULT_USER3 = 5;

    private List<Result> results = new ArrayList<>();

    public PollMaxResultServiceImpl() {
        results.add(new Result(USER1, MAX_RESULT_USER1));
        results.add(new Result(USER2, MAX_RESULT_USER2));
        results.add(new Result(USER3, MAX_RESULT_USER3));
    }

    @Override
    public List<Result> getMaxResultList() {
        return new ArrayList<>(results);
    }

    @Override
    public boolean addResult(String userName, int result) {
        Result res = new Result(userName, result);
        if (!results.contains(res)) return results.add(res);
        boolean removed = results.removeIf(r -> r.equals(res) && r.getMaxResult() < res.getMaxResult());
        if (removed) return results.add(res);
        return false;
    }
}
