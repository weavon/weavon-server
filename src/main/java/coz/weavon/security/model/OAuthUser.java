package coz.weavon.security.model;

import coz.weavon.security.helper.OAuthUserProvider;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthUser {

    private String registrationId;

    private String nickname;

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
