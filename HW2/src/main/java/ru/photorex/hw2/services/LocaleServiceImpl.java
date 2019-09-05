package ru.photorex.hw2.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleServiceImpl implements LocaleService {

    private final MessageSource ms;
    private final IOService consoleService;
    private Locale locale;

    public LocaleServiceImpl(MessageSource ms,
                             IOService consoleService,
                             @Value("#{ systemProperties['user.region'] }") Locale locale) {
        this.ms = ms;
        this.consoleService = consoleService;
        this.locale = locale;
    }

    @Override
    public Locale selectUserLocale() {
        String locale;
        consoleService.printString(getMessage("choose.locale"));
        while (true) {
            locale = consoleService.readString();
            if (locale.equals("en") || locale.equals("ru")) {
                break;
            }
            consoleService.printString(getMessage("bad.locale"));
        }
        Locale l = new Locale(locale);
        if (locale.equals("en")) {
            setLocale(Locale.ENGLISH);
            return Locale.ENGLISH;
        }
        setLocale(l);
        return l;
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
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
