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
- Rest exception will translate the message/label codes to message when creating instance.

```java
public class RestException extends RuntimeException {

    public RestException(String messageCode) {
        super(translateMessageCode(messageCode));
    }

    public RestException(String message, String... labelCodes) {
        super(translateMessageCode(message, labelCodes));
    }

    private static String translateMessageCode(String messageCode) {
        return RestException.getTranslator().translate(messageCode);
    }

    private static String translateMessageCode(String messageCode, String... labelCodes) {
        return RestException.getTranslator().translate(messageCode, labelCodes);
    }

    private static Translator getTranslator() {
        return ApplicationContextProvider.getBean(Translator.class);
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
