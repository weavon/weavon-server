# Exception

### Exception Type

- There are three exception types. Business, client, server exception.
- Business exception is an exception that is thrown in application layer.
- Client exception is an exception that is thrown in presentation layer.
- Server exception is an exception that is not predicted such as NullPointerException.

### Business Exception
- Business exception is an exception that is thrown in application layer.
- Throw business exception when something happens that is not expected in the business logic.
- Business exception should be thrown with a message, if needed with labels, and it will be translated automatically.
```java
throw new BusinessException("internationalize.message.code", "label1", "label2");
```

### Client Exception
- Client exception is an exception that is thrown in presentation layer.
- Throw client exception when something is not expected with client request.
- Client exception should be thrown with a message, if needed with labels, and it will be translated automatically.
```java
throw new ClientException("internationalize.message.code", "label1", "label2");
```

### Rest Exception
- All custom exceptions should extend RestException witch extends RuntimeException.
- Rest exception contains codes for internationalization.

```java
@Getter
public class RestException extends RuntimeException {

    private final String messageCode;

    private final List<String> labelCodes;

    public RestException(String message, String messageCode) {
        super(message);
        this.messageCode = messageCode;
        this.labelCodes = Collections.emptyList();
    }

    public RestException(String message, String messageCode, String... labelCodes) {
        super(message);
        this.messageCode = messageCode;
        this.labelCodes = List.of(labelCodes);
    }

    public boolean hasMessageCode() {
        return StringUtils.hasText(messageCode);
    }

    public boolean hasLabelCodes() {
        return !CollectionUtils.isEmpty(labelCodes);
    }
}
```

# Exception Handler

- All exceptions thrown will be handler in the global exception handler.
- Responses returned in global exception handler will return with ErrorResponse.
```java
@ExceptionHandler(BusinessException.class)
public RestResponse<ErrorResponse> handleBusinessException(BusinessException exception) {
    return RestResponse.ofBusinessError(exception.getMessage());
}
```
- Internationalization for exception message will be translated here.
```java
private String handleExceptionMessage(RestException exception) {
    String errorMessage = exception.getMessage();

    if (exception.hasMessageCode() && exception.hasLabelCodes()) {
        errorMessage = messageTranslator.translate(
                exception.getMessageCode(), exception.getLabelCodes().toArray(new String[0]));
    } else if (exception.hasMessageCode()) {
        errorMessage = messageTranslator.translate(exception.getMessageCode());
    }

    return errorMessage;
}
```
