package ru.phororex.hw1.services;

import lombok.RequiredArgsConstructor;
import ru.phororex.hw1.exceptions.NoCsvDataException;
import ru.phororex.hw1.model.Question;

import java.util.List;

@RequiredArgsConstructor
public class PollServiceImpl implements PollService {

    private final QuestionDataParser parser;
    private final ConsoleService consoleService;

    @Override
    public void startPollWithData() {
        try {
            List<Question> questions = parser.parseQuestions();
            consoleService.printString("Введите имя и фамилию");
            String name = consoleService.readString();
            int result = questions.stream().mapToInt(consoleService::printQuestions).sum();
            consoleService.printString(name + " дал " + result + " правильных ответа");
        } catch (NoCsvDataException ex) {
            consoleService.printString(ex.getLocalizedMessage());
        }
    }
}
