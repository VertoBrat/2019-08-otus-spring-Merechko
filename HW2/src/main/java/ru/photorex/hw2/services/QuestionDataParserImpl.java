package ru.photorex.hw2.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import ru.photorex.hw2.exceptions.NoCsvDataException;
import ru.photorex.hw2.model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionDataParserImpl implements QuestionDataParser {

    private final LocaleService ms;

    @Override
    public List<Question> parseQuestions(String filePath) throws NoCsvDataException {
        InputStream in = QuestionDataParserImpl.class.getClassLoader().getResourceAsStream(filePath);
        List<Question> questions = new ArrayList<>();
        String[] nameMapping = new String[]{"theQuestion", "answer"};
        try (ICsvBeanReader beanReader =
                     new CsvBeanReader(new BufferedReader(new InputStreamReader(in)), CsvPreference.STANDARD_PREFERENCE)) {
            Question question;
            while ((question = beanReader.read(Question.class, nameMapping)) != null) {
                questions.add(question);
            }
        } catch (IOException ex) {
            throw new NoCsvDataException(ms.getMessage("connection.fail"));
        } catch (IllegalArgumentException ex) {
            throw new NoCsvDataException(ms.getMessage("invalid.data"));
        }

        if (questions.size() == 0) throw new NoCsvDataException(ms.getMessage("no.data"));

        return questions;
    }
}
