package coz.weavon.exception.model;

public class BusinessException extends RestException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, String... labelCodes) {
        super(message, labelCodes);
    }
}
