package coz.weavon.common.application.model.exception;

public class BusinessException extends RestException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, String... labelCodes) {
        super(message, labelCodes);
    }
}
