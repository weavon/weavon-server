package coz.weavon.common.exception.model;

public class ClientException extends RestException {
    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, String... labelCodes) {
        super(message, labelCodes);
    }
}
