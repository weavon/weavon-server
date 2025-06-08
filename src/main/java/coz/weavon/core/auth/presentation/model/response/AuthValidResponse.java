package coz.weavon.core.auth.presentation.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthValidResponse {

    private String username;

    public static AuthValidResponse of(String username) {
        return AuthValidResponse.builder().username(username).build();
    }
}
