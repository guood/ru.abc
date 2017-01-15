package ru.abc.common.messages;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class Messages {

    private MessageSource messageSource;

    public Messages(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code, Object... params) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, params, currentLocale);
    }
}
