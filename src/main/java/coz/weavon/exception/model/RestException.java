package coz.weavon.exception.model;

import coz.weavon.message.Translator;
import coz.weavon.provider.ApplicationContextProvider;

public class RestException extends RuntimeException {

    public RestException(String messageCode) {
        super(translateMessageCode(messageCode));
    }

    public RestException(String message, String... labelCodes) {
        super(translateMessageCode(message, labelCodes));
    }

    private static String translateMessageCode(String messageCode) {
        return RestException.getTranslator().translate(messageCode);
    }

    private static String translateMessageCode(String messageCode, String... labelCodes) {
        return RestException.getTranslator().translate(messageCode, labelCodes);
    }

    private static Translator getTranslator() {
        return ApplicationContextProvider.getBean(Translator.class);
    }
}
