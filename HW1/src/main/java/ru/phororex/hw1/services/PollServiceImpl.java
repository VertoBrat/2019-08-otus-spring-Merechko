package ru.phororex.hw1.services;

import lombok.RequiredArgsConstructor;
import ru.phororex.hw1.exceptions.NoCsvDataException;
import ru.phororex.hw1.model.Question;

import java.util.List;

@RequiredArgsConstructor
public class PollServiceImpl implements PollService {

    private final QuestionDataParser parser;
    private final ConsoleHelper consoleHelper;

    @Override
    public void startPollWithData() {
        try {
            List<Question> questions = parser.parseQuestions();
            consoleHelper.printQuestions(questions);
        } catch (NoCsvDataException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
}
