package ru.photorex.hw4.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import ru.photorex.hw4.exceptions.NoCsvDataException;
import ru.photorex.hw4.model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static ru.photorex.hw4.utils.Messages.*;

@Service
@RequiredArgsConstructor
public class QuestionDataParserImpl implements QuestionDataParser {

    private static final String QUESTION = "theQuestion";
    private static final String ANSWER = "answer";

    private final LocaleService ms;

    @Override
    public List<Question> parseQuestions(String filePath) throws NoCsvDataException {
        List<Question> questions = new ArrayList<>();
        String[] nameMapping = new String[]{QUESTION, ANSWER};
        try (InputStream in = QuestionDataParserImpl.class.getClassLoader().getResourceAsStream(filePath);
             ICsvBeanReader beanReader =
                     new CsvBeanReader(new BufferedReader(new InputStreamReader(in)), CsvPreference.STANDARD_PREFERENCE)) {
            Question question;
            while ((question = beanReader.read(Question.class, nameMapping)) != null) {
                questions.add(question);
            }
        } catch (IOException ex) {
            throw new NoCsvDataException(ms.getMessage(CONNECTION_FAIL));
        } catch (IllegalArgumentException ex) {
            throw new NoCsvDataException(ms.getMessage(INVALID_DATA));
        } catch (NullPointerException ex) {
            throw new NoCsvDataException(ms.getMessage(NONEXISTENT_LOCALE));
        }

        if (questions.size() == 0) throw new NoCsvDataException(ms.getMessage(NO_DATA_IN_FILE));

        return questions;
    }
}
