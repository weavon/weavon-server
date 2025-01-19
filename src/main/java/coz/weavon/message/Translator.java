package coz.weavon.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Translator {

    private final MessageSource messageSource;

    public String translate(String messageCode) {
        return messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale());
    }

    public String translate(String messageCode, String... labelCodes) {
        return messageSource.getMessage(messageCode, labelCodes, LocaleContextHolder.getLocale());
    }
}
