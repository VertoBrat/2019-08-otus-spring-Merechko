package ru.photorex.hw2.services;
import ru.photorex.hw2.exceptions.NoCsvDataException;
import ru.photorex.hw2.model.Question;

import java.util.List;

public interface QuestionDataParser {

    List<Question> parseQuestions(String filePath) throws NoCsvDataException;
}
