package ru.photorex.hw4.services;
import ru.photorex.hw4.exceptions.NoCsvDataException;
import ru.photorex.hw4.model.Question;

import java.util.List;

public interface QuestionDataParser {

    List<Question> parseQuestions(String filePath) throws NoCsvDataException;
}
