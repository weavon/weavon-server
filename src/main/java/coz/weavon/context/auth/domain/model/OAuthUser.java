package coz.weavon.context.auth.domain.model;

import coz.weavon.common.domain.model.Property;
import coz.weavon.context.auth.domain.service.OAuthUserConstructor;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthUser {

    @Property(nullable = false, updatable = false)
    private RegistrationProvider registrationProvider;

    @Property(nullable = false, updatable = false)
    private String nickname;

    @Property(unique = true, nullable = false, updatable = false)
    private String email;

    public static OAuthUser of(RegistrationProvider registrationClient, String nickname, String email) {
        return OAuthUser.builder()
                .registrationProvider(registrationClient)
                .nickname(nickname)
                .email(email)
                .build();
    }

    public static OAuthUser ofAttributes(String registrationId, Map<String, Object> attributes) {
        return OAuthUserConstructor.constructOAuthUser(registrationId, attributes);
    }
}
