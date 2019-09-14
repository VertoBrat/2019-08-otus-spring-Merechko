package ru.photorex.hw4.services;

import org.springframework.stereotype.Service;
import ru.photorex.hw4.model.Result;

import java.util.ArrayList;
import java.util.List;

@Service
public class PollMaxResultServiceImpl implements PollMaxResultService {

    private List<Result> results = new ArrayList<>();

    public PollMaxResultServiceImpl() {
        results.add(new Result("Dima", 3));
        results.add(new Result("Goga", 0));
        results.add(new Result("Igor", 5));
    }

    @Override
    public List<Result> getMaxResultList() {
        return new ArrayList<>(results);
    }

    @Override
    public boolean addResult(String userName, int result) {
        Result res = new Result(userName, result);
        if (!results.contains(res)) return results.add(res);
        boolean removed = results.removeIf(r->r.equals(res) && r.getMaxResult() < res.getMaxResult());
        if (removed) return results.add(res);
        return false;
    }
}
