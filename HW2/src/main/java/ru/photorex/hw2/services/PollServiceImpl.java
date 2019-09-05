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
    private final String ruFilePath;
    private final String enFilePath;
    private final LocaleService ms;

    private String usePath;

    public PollServiceImpl(QuestionDataParser parser,
                           IOService consoleService,
                           @Value("${ruFilePath}") String ruFilePath,
                           @Value("${enFilePath}") String enFilePath,
                           LocaleService ms) {
        this.parser = parser;
        this.consoleService = consoleService;
        this.ruFilePath = ruFilePath;
        this.enFilePath = enFilePath;
        this.ms = ms;
    }

    @Override
    public void startPollWithData() {
        try {
            selectUserLocaleAndFileForPoll();
            List<Question> questions = parser.parseQuestions(usePath);
            consoleService.printString(ms.getMessage("fio"));
            String name = consoleService.readString();
            int result = questions.stream().mapToInt(this::printQuestions).sum();
            consoleService.printString(ms.getMessage("result", new String[]{name, Integer.toString(result)}));
        } catch (NoCsvDataException ex) {
            consoleService.printString(ex.getLocalizedMessage());
        }
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

    private void selectUserLocaleAndFileForPoll() {
        String locale = "";
        consoleService.printString(ms.getMessage("choose.locale"));
        while (true) {
            locale = consoleService.readString();
            if (locale.equals("en") || locale.equals("ru")) {
                break;
            }
            consoleService.printString(ms.getMessage("bad.locale"));
        }
        ms.setLocale(new Locale(locale));
        if (locale.equals("en"))
            setUsePath(enFilePath);
        else setUsePath(ruFilePath);
    }

    private void setUsePath(String path) {
        this.usePath = path;
    }
}
