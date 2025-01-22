package coz.weavon.common.application.model.exception;

import coz.weavon.common.application.service.MessageTranslator;
import coz.weavon.config.provider.ApplicationContextProvider;

public class RestException extends RuntimeException {

    public RestException(String messageCode) {
        super(translateMessageCode(messageCode));
    }

    public RestException(String message, String... labelCodes) {
        super(translateMessageCode(message, labelCodes));
    }

    private static String translateMessageCode(String messageCode) {
        return RestException.getMessageTranslator().translate(messageCode);
    }

    private static String translateMessageCode(String messageCode, String... labelCodes) {
        return RestException.getMessageTranslator().translate(messageCode, labelCodes);
    }

    private static MessageTranslator getMessageTranslator() {
        return ApplicationContextProvider.getBean(MessageTranslator.class);
    }
}
