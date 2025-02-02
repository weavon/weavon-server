package coz.weavon.context.security.domain.model;

import coz.weavon.common.domain.model.Property;
import coz.weavon.context.security.domain.service.AuthTokenGenerator;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthToken {

    @Property(unique = true, nullable = false)
    private String value;

    public static AuthToken of(String value) {
        return AuthToken.builder().value(value).build();
    }

    public static AuthToken ofAuthUser(AuthUser authUser) {
        return AuthTokenGenerator.generateAuthToken(authUser.getUsername(), authUser.getRole());
    }
}
