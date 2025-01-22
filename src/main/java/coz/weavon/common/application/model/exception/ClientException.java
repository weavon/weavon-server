package coz.weavon.common.application.model.exception;

public class ClientException extends RestException {
    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, String... labelCodes) {
        super(message, labelCodes);
    }
}
