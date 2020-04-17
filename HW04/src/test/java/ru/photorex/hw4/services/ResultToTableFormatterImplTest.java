package ru.photorex.hw4.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.photorex.hw4.model.Result;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("Сервис, отображающий таблицу")
@SpringBootTest
public class ResultToTableFormatterImplTest {

    private static final String USER = "User";
    private static final int USER_RECORD = 2;

    @Autowired
    private ResultToTableFormatter formatter;

    @MockBean
    private PollMaxResultService pollMaxResultService;

    @MockBean
    private IOService console;

    @MockBean
    private LocaleService localeService;

    @Test
    @DisplayName("должен вызвать по одному разу методы сервисов")
    void shouldCallMethodsOneTimePerService() {
        given(pollMaxResultService.getMaxResultList()).willReturn(new ArrayList<>());
        formatter.format(pollMaxResultService.getMaxResultList());
        verify(console, times(1)).printString(any());
        verify(localeService, times(1)).getMessage(any());
    }

    @Test
    @DisplayName("должен вызывать метод печати больше одного раза")
    void shouldCallPrintMethodMoreThanOneTime() {
        List<Result> list = List.of(new Result(USER, USER_RECORD));
        given(pollMaxResultService.getMaxResultList()).willReturn(list);
        formatter.format(pollMaxResultService.getMaxResultList());
        verify(console, atLeast(2)).printString(any());
        verify(localeService, times(1)).getMessage(any());
    }
}
