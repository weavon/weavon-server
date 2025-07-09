package coz.weavon.common.exception;

public class BusinessException extends RestException {

    private static final String BUSINESS_ERROR = "Business Error";

    public BusinessException(String messageCode) {
        super(BUSINESS_ERROR, messageCode);
    }

    public BusinessException(String messageCode, String... labelCodes) {
        super(BUSINESS_ERROR, messageCode, labelCodes);
    }
}
