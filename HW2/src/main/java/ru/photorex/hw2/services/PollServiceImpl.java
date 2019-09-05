package ru.photorex.hw2.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.photorex.hw2.exceptions.NoCsvDataException;
import ru.photorex.hw2.model.Question;

import java.util.List;
import java.util.Locale;

@Service
public class PollServiceImpl implements PollService {

    private final QuestionDataParser parser;
    private final IOService consoleService;
    private final String filePath;
    private final LocaleService ms;

    public PollServiceImpl(QuestionDataParser parser,
                           IOService consoleService,
                           @Value("${filePath}") String filePath,
                           LocaleService ms) {
        this.parser = parser;
        this.consoleService = consoleService;
        this.filePath = filePath;
        this.ms = ms;
    }

    @Override
    public void startPollWithData() {
        try {
            List<Question> questions = parser.parseQuestions(filePath);
            selectUserLocale();
            consoleService.printString(ms.getMessage("fio"));
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

    private void selectUserLocale() {
        String locale = "";
        consoleService.printString(ms.getMessage("chooseLocale"));
        while (true) {
            locale = consoleService.readString();
            if (locale.equals("en") || locale.equals("ru"))
                break;
            consoleService.printString(ms.getMessage("badLocale"));
        }
        ms.setLocale(new Locale(locale));
    }
}
