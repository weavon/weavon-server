package coz.weavon.io.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SuccessResponse<T> {

    private T value;

    public static <T> SuccessResponse<T> of(T value) {
        return SuccessResponse.<T>builder().value(value).build();
    }
}
