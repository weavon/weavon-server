package coz.weavon.common.exception.handler;

import coz.weavon.common.exception.model.BusinessException;
import coz.weavon.common.exception.model.ClientException;
import coz.weavon.common.io.model.response.ErrorResponse;
import coz.weavon.common.io.model.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
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
