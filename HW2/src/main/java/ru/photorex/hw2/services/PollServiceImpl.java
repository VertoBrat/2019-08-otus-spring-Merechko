package ru.photorex.hw2.services;

import org.springframework.stereotype.Service;
import ru.photorex.hw2.exceptions.NoCsvDataException;
import ru.photorex.hw2.model.Question;

import java.util.List;

@Service
public class PollServiceImpl implements PollService {

    private final QuestionDataParser parser;
    private final IOService consoleService;
    private final LocaleService ms;

    private String usePath;

    public PollServiceImpl(QuestionDataParser parser,
                           IOService consoleService,
                           LocaleService ms) {
        this.parser = parser;
        this.consoleService = consoleService;
        this.ms = ms;
    }

    @Override
    public void startPollWithData() {
        try {
            selectUserLocale();
            usePath = ms.getMessage("locale");
            List<Question> questions = parser.parseQuestions(usePath);
            consoleService.printString(ms.getMessage("fio"));
            String name = consoleService.readString();
            int result = questions.stream().mapToInt(this::printQuestions).sum();
            consoleService.printString(ms.getMessage("result", new String[]{name, Integer.toString(result)}));
        } catch (NoCsvDataException ex) {
            consoleService.printString(ex.getLocalizedMessage());
        }
    }

    private void selectUserLocale() {
        String locale;
        consoleService.printString(ms.getMessage("choose.locale"));
        while (true) {
            locale = consoleService.readString().toLowerCase();
            if (locale.length() > 1 && locale.length() <= 8)
                break;
            consoleService.printString(ms.getMessage("bad.locale"));
        }
        ms.setLocale(locale);
    }

    private int printQuestions(Question question) {
        consoleService.printString(question.getTheQuestion());
        if (consoleService.readString().toLowerCase().equals(question.getAnswer())) {
            consoleService.printString(ms.getMessage("right.answer"));
            return 1;
        } else {
            consoleService.printString(ms.getMessage("bad.answer", new String[]{question.getAnswer()}));
            return 0;
        }
    }

    private void setUsePath(String path) {
        this.usePath = path;
    }
}
