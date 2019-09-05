package ru.photorex.hw2.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.photorex.hw2.services.utils.ConsoleServiceHelper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

public class LocaleServiceTest {

    private static final String BUNDLE_PATH = "/i18n/bundle";
    private static final String ENCODING = "UTF-8";
    private static final String RU_LOCALE = "ru";
    private static final String RIGHT_ANSWER = "right.answer";
    private static final String EN_ANSWER = "Great right";
    private static final String RU_ANSWER = "Верно";
    private static final String RESULT = "result";
    private static final String NAME = "Some name";
    private static final int result = 5;
    private static final String RESULT_EN_STRING = NAME + " gave " + result + " correct answers";
    private static final String RESULT_RU_STRING = NAME + " дал " + result + " правильных ответов";

    private LocaleService localeService;

    @BeforeEach
    void initLocaleService() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename(BUNDLE_PATH);
        ms.setDefaultEncoding(ENCODING);
        ConsoleService cs = ConsoleServiceHelper.getInstance(RU_LOCALE);
        localeService = new LocaleServiceImpl(ms, cs, Locale.ENGLISH);
    }

    @Test
    void selectUserLocale() {
        assertThat(new Locale(RU_LOCALE)).isEqualTo(localeService.selectUserLocale());
    }

    @Test
    void getEnglishMessageWithoutArgs() {
        assertThat(EN_ANSWER).isEqualTo(localeService.getMessage(RIGHT_ANSWER));
    }

    @Test
    void getRussianMessageWithoutArgs() {
        localeService.setLocale(new Locale(RU_LOCALE));
        assertThat(RU_ANSWER).isEqualTo(localeService.getMessage(RIGHT_ANSWER));
    }

    @Test
    void getEnglishMessageWithArgs() {
        assertThat(RESULT_EN_STRING).isEqualTo(localeService.getMessage(RESULT, new String[]{NAME, Integer.toString(result)}));
    }

    @Test
    void getRussianMessageWithArgs() {
        localeService.setLocale(new Locale(RU_LOCALE));
        assertThat(RESULT_RU_STRING).isEqualTo(localeService.getMessage(RESULT, new String[]{NAME, Integer.toString(result)}));
    }
}
