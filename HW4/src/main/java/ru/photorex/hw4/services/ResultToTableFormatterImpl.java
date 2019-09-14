package ru.photorex.hw4.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.photorex.hw4.model.Result;

import java.util.List;

import static ru.photorex.hw4.utils.Messages.HIGHSCORES;
import static ru.photorex.hw4.utils.Messages.NORESULTS;

@Service
@RequiredArgsConstructor
public class ResultToTableFormatterImpl implements ResultToTableFormatter {

    private static final String LINE = "==============";

    private final IOService console;
    private final LocaleService ms;

    @Override
    public void format(List<Result> results) {
        if (results.isEmpty()) {
            console.printString(ms.getMessage(NORESULTS));
            return;
        }
        console.printString(LINE);
        console.printString("  " + ms.getMessage(HIGHSCORES) + " ");
        console.printString(LINE);
        results.stream().sorted().forEach(r->console.printFormattedString("| %6s |  %1d", r.getUserName(), r.getMaxResult()));
        console.printString(LINE);
    }
}
