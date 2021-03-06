package ru.photorex.hw3.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.photorex.hw3.exceptions.NoCsvDataException;
import ru.photorex.hw3.model.Question;

import java.util.IllformedLocaleException;
import java.util.List;

import static ru.photorex.hw3.utils.Messages.*;

@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {

    private static final String PATH = "data_";
    private static final String TYPE = ".csv";
    private static final int MIN_NUM_OF_LOCALE_CHAR = 1;
    private static final int MAX_NUM_OF_LOCALE_CHAR = 8;

    private final QuestionDataParser parser;
    private final IOService consoleService;
    private final LocaleService ms;

    @Override
    public void startPollWithData() {
        try {
            String localePath = selectUserLocale();
            String usePath = PATH + localePath + TYPE;
            List<Question> questions = parser.parseQuestions(usePath);
            consoleService.printString(ms.getMessage(INPUT_NAME));
            String name = consoleService.readString();
            int result = questions.stream().mapToInt(this::printQuestions).sum();
            consoleService.printString(ms.getMessage(RESULT, new String[]{name, Integer.toString(result)}));
        } catch (NoCsvDataException ex) {
            consoleService.printString(ex.getLocalizedMessage());
        } catch (IllformedLocaleException ex) {
            consoleService.printString(ms.getMessage(INVALID_LANG));
        }
    }

    private String selectUserLocale() {
        String locale;
        consoleService.printString(ms.getMessage(CHOOSE_LOCALE));
        while (true) {
            locale = consoleService.readString().toLowerCase();
            if (locale.length() > MIN_NUM_OF_LOCALE_CHAR && locale.length() <= MAX_NUM_OF_LOCALE_CHAR)
                break;
            consoleService.printString(ms.getMessage(NONEXISTENT_LOCALE));
        }
        ms.setLocale(locale);
        return locale;
    }

    private int printQuestions(Question question) {
        consoleService.printString(question.getTheQuestion());
        if (consoleService.readString().toLowerCase().equals(question.getAnswer())) {
            consoleService.printString(ms.getMessage(RIGHT_ANSWER));
            return 1;
        } else {
            consoleService.printString(ms.getMessage(BAD_ANSWER, new String[]{question.getAnswer()}));
            return 0;
        }
    }
}
