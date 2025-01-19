package coz.weavon.common.io.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestResponse<T> {

    private ResponseType type;
    private T value;

    public static <T> RestResponse<SuccessResponse<T>> ofSuccess(T value) {
        return RestResponse.<SuccessResponse<T>>builder()
                .type(ResponseType.SUCCESS)
                .value(SuccessResponse.of(value))
                .build();
    }

    public static RestResponse<ErrorResponse> ofBusinessError(String message) {
        return RestResponse.<ErrorResponse>builder()
                .type(ResponseType.BUSINESS_ERROR)
                .value(ErrorResponse.of(message))
                .build();
    }

    public static RestResponse<ErrorResponse> ofClientError(String message) {
        return RestResponse.<ErrorResponse>builder()
                .type(ResponseType.CLIENT_ERROR)
                .value(ErrorResponse.of(message))
                .build();
    }

    public static RestResponse<ErrorResponse> ofServerError(String message) {
        return RestResponse.<ErrorResponse>builder()
                .type(ResponseType.SERVER_ERROR)
                .value(ErrorResponse.of(message))
                .build();
    }
}
