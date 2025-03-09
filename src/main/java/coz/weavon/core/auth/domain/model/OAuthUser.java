package coz.weavon.core.auth.domain.model;

import coz.weavon.common.domain.model.Property;
import coz.weavon.core.auth.domain.service.OAuthUserProvider;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthUser {

    @Property(nullable = false, updatable = false)
    private String registrationId;

    @Property(nullable = false, updatable = false)
    private String nickname;

    @Property(unique = true, nullable = false, updatable = false)
    private String email;

    public static OAuthUser of(String registrationId, String nickname, String email) {
        return OAuthUser.builder()
                .registrationId(registrationId)
                .nickname(nickname)
                .email(email)
                .build();
    }

    public static OAuthUser ofAttributes(String registrationId, Map<String, Object> attributes) {
        return OAuthUserProvider.provideOAuthUser(registrationId, attributes);
    }
}
