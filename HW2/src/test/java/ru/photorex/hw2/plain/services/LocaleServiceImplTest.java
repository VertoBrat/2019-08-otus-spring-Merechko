package ru.photorex.hw2.plain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.photorex.hw2.services.LocaleService;
import ru.photorex.hw2.services.LocaleServiceImpl;


import java.util.Locale;

import static ru.photorex.hw2.utils.Messages.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис для работы с локалью")
@ExtendWith(MockitoExtension.class)
public class LocaleServiceImplTest {

    @Mock
    private MessageSource ms;

    private LocaleService ls;

    @BeforeEach
    void setUp() {
        ls = new LocaleServiceImpl(ms, Locale.ENGLISH);
    }

    @Test
    @DisplayName("должен вызывать метод MessageResource")
    void shouldExecuteServiceMethodWithOneArgument() {
        ls.getMessage(INPUT_NAME);
        verify(ms, times(1)).getMessage(INPUT_NAME.getMessage(), null, Locale.ENGLISH);
    }

    @Test
    @DisplayName("должен вызывать метод MessageResource")
    void shouldExecuteServiceMethodWithTwoArguments() {
        String[] args = new String[]{"",""};
        ls.getMessage(INPUT_NAME, args);
        verify(ms, times(1)).getMessage(INPUT_NAME.getMessage(), args, Locale.ENGLISH);
    }
}
