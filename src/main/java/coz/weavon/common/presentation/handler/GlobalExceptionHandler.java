package coz.weavon.common.presentation.handler;

import coz.weavon.common.application.model.exception.AuthException;
import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.common.application.model.exception.ClientException;
import coz.weavon.common.application.model.exception.RestException;
import coz.weavon.common.application.service.MessageTranslator;
import coz.weavon.common.presentation.model.response.ErrorResponse;
import coz.weavon.common.presentation.model.response.RestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageTranslator messageTranslator;

    @ExceptionHandler(AuthException.class)
    public RestResponse<ErrorResponse> handleAuthException(AuthException exception) {
        String exceptionMessage = this.handleExceptionMessage(exception);
        log.error("Authentication error occurred : {}", exceptionMessage);
        return RestResponse.ofAuthError(exceptionMessage);
    }

    @ExceptionHandler(BusinessException.class)
    public RestResponse<ErrorResponse> handleBusinessException(BusinessException exception) {
        String exceptionMessage = this.handleExceptionMessage(exception);
        log.error("Business error occurred : {}", exceptionMessage);
        return RestResponse.ofBusinessError(exceptionMessage);
    }

    @ExceptionHandler(ClientException.class)
    public RestResponse<ErrorResponse> handleClientException(ClientException exception) {
        String exceptionMessage = this.handleExceptionMessage(exception);
        log.error("Client error occurred : {}", exceptionMessage);
        return RestResponse.ofClientError(exceptionMessage);
    }

    @ExceptionHandler(Exception.class)
    public RestResponse<ErrorResponse> handleException(Exception exception) {
        log.error("Unexpected server error occurred : {}", exception.getMessage());
        return RestResponse.ofServerError(exception.getMessage());
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
