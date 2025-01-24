package coz.weavon.common.application.model.exception;

public class BusinessException extends RestException {

    private static final String BUSINESS_ERROR = "Business Error";

    public BusinessException(String messageCode) {
        super(BUSINESS_ERROR, messageCode);
    }

    public BusinessException(String messageCode, String... labelCodes) {
        super(BUSINESS_ERROR, messageCode, labelCodes);
    }
}
