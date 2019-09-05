package ru.photorex.hw2.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.photorex.hw2.exceptions.NoCsvDataException;
import ru.photorex.hw2.model.Question;

import java.util.List;

@Service
public class PollServiceImpl implements PollService {

    private final QuestionDataParser parser;
    private final IOService consoleService;
    private final String filePath;

    public PollServiceImpl(QuestionDataParser parser,
                           IOService consoleService,
                           @Value("${filePath}") String filePath) {
        this.parser = parser;
        this.consoleService = consoleService;
        this.filePath = filePath;
    }

    @Override
    public void startPollWithData() {
        try {
            List<Question> questions = parser.parseQuestions(filePath);
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
