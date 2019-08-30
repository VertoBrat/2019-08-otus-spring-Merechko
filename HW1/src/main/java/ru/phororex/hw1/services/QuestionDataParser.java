package ru.phororex.hw1.services;

import ru.phororex.hw1.NoCsvDataException;
import ru.phororex.hw1.model.Question;

import java.util.List;

public interface QuestionDataParser {

    List<Question> parseQuestions() throws NoCsvDataException;
}
