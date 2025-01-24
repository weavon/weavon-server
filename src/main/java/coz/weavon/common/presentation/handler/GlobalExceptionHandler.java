package coz.weavon.common.presentation.handler;

import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.common.application.model.exception.ClientException;
import coz.weavon.common.application.model.exception.RestException;
import coz.weavon.common.application.service.MessageTranslator;
import coz.weavon.common.presentation.model.response.ErrorResponse;
import coz.weavon.common.presentation.model.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageTranslator messageTranslator;

    @ExceptionHandler(BusinessException.class)
    public RestResponse<ErrorResponse> handleBusinessException(BusinessException exception) {
        String exceptionMessage = this.handleExceptionMessage(exception);
        return RestResponse.ofBusinessError(exceptionMessage);
    }

    @ExceptionHandler(ClientException.class)
    public RestResponse<ErrorResponse> handleClientException(ClientException exception) {
        String exceptionMessage = this.handleExceptionMessage(exception);
        return RestResponse.ofClientError(exceptionMessage);
    }

    @ExceptionHandler(Exception.class)
    public RestResponse<ErrorResponse> handleException(Exception exception) {
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
