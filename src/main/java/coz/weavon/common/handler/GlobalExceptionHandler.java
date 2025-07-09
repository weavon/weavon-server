package coz.weavon.common.handler;

import coz.weavon.common.exception.AuthException;
import coz.weavon.common.exception.BusinessException;
import coz.weavon.common.exception.ClientException;
import coz.weavon.common.exception.RestException;
import coz.weavon.common.io.ErrorResponse;
import coz.weavon.common.io.RestResponse;
import coz.weavon.helper.MessageTranslator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final String MSG_VLD_BAD_CLIENT_REQUEST = "message.validation.badUserRequest";

    private final MessageTranslator messageTranslator;

    @ExceptionHandler(AuthException.class)
    public RestResponse<ErrorResponse> handleAuthException(AuthException exception) {
        String exceptionMessage = this.handleExceptionMessage(exception);
        log.error("Authentication error occurred : {}", exceptionMessage);
        return RestResponse.ofAuthError(exceptionMessage);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<RestResponse<ErrorResponse>> handleBusinessException(BusinessException exception) {
        String exceptionMessage = this.handleExceptionMessage(exception);
        log.error("Business error occurred : {}", exceptionMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RestResponse.ofBusinessError(exceptionMessage));
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<RestResponse<ErrorResponse>> handleClientException(ClientException exception) {
        String exceptionMessage = this.handleExceptionMessage(exception);
        log.error("Client error occurred : {}", exceptionMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestResponse.ofClientError(exceptionMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<ErrorResponse>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        log.error("Validation error occurred : {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RestResponse.ofClientError(messageTranslator.translate(MSG_VLD_BAD_CLIENT_REQUEST)));
    }

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
}
