package ru.photorex.hw3.services;
import ru.photorex.hw3.exceptions.NoCsvDataException;
import ru.photorex.hw3.model.Question;

import java.util.List;

public interface QuestionDataParser {

    List<Question> parseQuestions(String filePath) throws NoCsvDataException;
}
