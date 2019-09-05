package ru.photorex.hw2.services.utils;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.photorex.hw2.services.ConsoleService;
import ru.photorex.hw2.services.LocaleService;
import ru.photorex.hw2.services.LocaleServiceImpl;

import java.util.Locale;

public class LocaleServiceHelper {

    private static final String BUNDLE_PATH = "/i18n/bundle";
    private static final String ENCODING = "UTF-8";

    private LocaleServiceHelper() {}

    public static LocaleService getInstance() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename(BUNDLE_PATH);
        ms.setDefaultEncoding(ENCODING);
        ConsoleService cs = ConsoleServiceHelper.getInstance("ru");
        return new LocaleServiceImpl(ms, cs, Locale.ENGLISH);
    }
}
