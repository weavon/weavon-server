package coz.weavon.context.auth.presentation.model;

import coz.weavon.context.auth.domain.model.AuthUser;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthValidResponse {

    private String username;

    public static AuthValidResponse of(AuthUser authUser) {
        return AuthValidResponse.builder().username(authUser.getUsername()).build();
    }
}
