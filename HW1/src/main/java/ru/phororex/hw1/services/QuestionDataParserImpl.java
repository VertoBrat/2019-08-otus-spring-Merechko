package ru.phororex.hw1.services;

import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import ru.phororex.hw1.NoCsvDataException;
import ru.phororex.hw1.model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuestionDataParserImpl implements QuestionDataParser {

    @Override
    public List<Question> parseQuestions() throws NoCsvDataException {
        InputStream in = QuestionDataParserImpl.class.getClassLoader().getResourceAsStream("data.csv");
        List<Question> questions = new ArrayList<>();
        String[] nameMapping = new String[]{"theQuestion", "answer"};
        try (ICsvBeanReader beanReader =
                     new CsvBeanReader(new BufferedReader(new InputStreamReader(in)), CsvPreference.STANDARD_PREFERENCE)) {
            Question question;
            while ((question = beanReader.read(Question.class, nameMapping)) != null) {
                questions.add(question);
            }
        } catch (IOException ex) {
            throw new NoCsvDataException("Проблемы с подключением к файлу");
        } catch (IllegalArgumentException ex) {
            throw new NoCsvDataException("Невалидные данные в файле");
        }

        if (questions.size() == 0) throw new NoCsvDataException("в файле нет информации");

        return questions;
    }
}
