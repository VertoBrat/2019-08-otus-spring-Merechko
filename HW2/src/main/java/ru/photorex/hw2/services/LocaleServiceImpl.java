package ru.photorex.hw2.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class LocaleServiceImpl implements LocaleService {

    private final MessageSource ms;
    private final IOService consoleService;
    private Locale locale = Locale.ENGLISH;

    @Override
    public Locale selectUserLocaleAndFileForPoll() {
        String locale = "";
        consoleService.printString(getMessage("choose.locale"));
        while (true) {
            locale = consoleService.readString();
            if (locale.equals("en") || locale.equals("ru")) {
                break;
            }
            consoleService.printString(getMessage("bad.locale"));
        }
        Locale l = new Locale(locale);
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
