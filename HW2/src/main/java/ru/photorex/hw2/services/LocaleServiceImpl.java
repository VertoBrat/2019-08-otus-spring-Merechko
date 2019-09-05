package ru.photorex.hw2.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class LocaleServiceImpl implements LocaleService {

    private final MessageSource ms;
    private Locale locale = Locale.ENGLISH;

    @Override
    public String getMessage(String message) {
        return ms.getMessage(message, null, locale);
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
