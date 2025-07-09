package coz.weavon.common.exception;

public class AuthException extends RestException {

    public static final String AUTH_ERROR = "Authentication Error";

    public AuthException(String messageCode) {
        super(AUTH_ERROR, messageCode);
    }

    public AuthException(String messageCode, String... labelCodes) {
        super(AUTH_ERROR, messageCode, labelCodes);
    }
}
