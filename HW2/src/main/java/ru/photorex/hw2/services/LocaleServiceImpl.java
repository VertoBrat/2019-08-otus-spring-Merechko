package ru.photorex.hw2.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleServiceImpl implements LocaleService {

    private final MessageSource ms;
    private Locale locale;

    public LocaleServiceImpl(MessageSource ms,
                             @Value("#{ systemProperties['user.region'] }") Locale locale) {
        this.ms = ms;
        this.locale = locale;
    }

    @Override
    public String getMessage(String message, String[] args) {
        return ms.getMessage(message, args, locale);
    }

    @Override
    public String getMessage(String message) {
        return ms.getMessage(message, null, locale);
    }

    @Override
    public void setLocale(String locale) {
        this.locale = new Locale.Builder().setLanguage(locale).build();
    }
}
