package ru.phororex.hw1.services;

import lombok.RequiredArgsConstructor;
import ru.phororex.hw1.exceptions.NoCsvDataException;
import ru.phororex.hw1.model.Question;

import java.util.List;

@RequiredArgsConstructor
public class PollServiceImpl implements PollService {

    private final QuestionDataParser parser;
    private final IOService consoleService;

    @Override
    public void startPollWithData() {
        try {
            List<Question> questions = parser.parseQuestions();
            consoleService.printString("Введите имя и фамилию");
            String name = consoleService.readString();
            int result = questions.stream().mapToInt(this::printQuestions).sum();
            consoleService.printString(name + " дал " + result + " правильных ответа");
        } catch (NoCsvDataException ex) {
            consoleService.printString(ex.getLocalizedMessage());
        }
    }

    private int printQuestions(Question question) {
        consoleService.printString(question.getTheQuestion());
        if (consoleService.readString().toLowerCase().equals(question.getAnswer())) {
            consoleService.printString("Верно");
            return 1;
        } else {
            consoleService.printString("Нет, правильный ответ " + question.getAnswer());
            return 0;
        }
    }
}
