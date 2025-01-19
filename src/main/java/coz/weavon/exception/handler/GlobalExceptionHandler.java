package coz.weavon.exception.handler;

import coz.weavon.exception.model.BusinessException;
import coz.weavon.exception.model.ClientException;
import coz.weavon.io.model.ErrorResponse;
import coz.weavon.io.model.RestResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public RestResponse<ErrorResponse> handleBusinessException(BusinessException exception) {
        return RestResponse.ofBusinessError(exception.getMessage());
    }

    @ExceptionHandler(ClientException.class)
    public RestResponse<ErrorResponse> handleClientException(ClientException exception) {
        return RestResponse.ofClientError(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public RestResponse<ErrorResponse> handleException(Exception exception) {
        return RestResponse.ofServerError(exception.getMessage());
    }
}
