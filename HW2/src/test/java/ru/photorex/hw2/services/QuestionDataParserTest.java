package ru.photorex.hw2.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.photorex.hw2.exceptions.NoCsvDataException;
import ru.photorex.hw2.model.Question;
import ru.photorex.hw2.services.utils.LocaleServiceHelper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionDataParserTest {

    private static final String PATH = "data.csv";

    private QuestionDataParser qdp;

    @BeforeEach
    void initParser() {
        qdp = new QuestionDataParserImpl(LocaleServiceHelper.getInstance());
    }

    @Test
    void parseQuestions() {
        List<Question> questions = qdp.parseQuestions(PATH);
        assertThat(questions).hasSize(5);
    }

    @Test
    void parseQuestionsWithoutPath() {
        assertThrows(NoCsvDataException.class,() -> qdp.parseQuestions(""));
    }
}
