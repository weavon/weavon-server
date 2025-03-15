package coz.weavon.exception.model;

public class ClientException extends RestException {

    private static final String CLIENT_ERROR = "Client Error";

    public ClientException(String messageCode) {
        super(CLIENT_ERROR, messageCode);
    }

    public ClientException(String messageCode, String... labelCodes) {
        super(CLIENT_ERROR, messageCode, labelCodes);
    }
}
