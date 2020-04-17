package ru.photorex.hw3.services;

import ru.photorex.hw3.utils.Messages;

public interface LocaleService {

    String getMessage(Messages property);

    String getMessage(Messages property, String[] args);

    void setLocale(String locale);
}
