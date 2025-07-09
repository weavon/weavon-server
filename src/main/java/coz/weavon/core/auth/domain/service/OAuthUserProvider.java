package coz.weavon.core.auth.domain.service;

import coz.weavon.common.exception.BusinessException;
import coz.weavon.core.auth.domain.model.OAuthUser;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OAuthUserProvider {

    private static final String GOOGLE_REGISTRATION_ID = "google";

    private static final String NAVER_REGISTRATION_ID = "naver";

    private static final String MSG_AUTH_OAUTH_UNSUPPORTED_REGISTRATION_PROVIDER =
            "message.authentication.oauth.unsupportedRegistrationProvider";

    public static OAuthUser provideOAuthUser(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) {
            case GOOGLE_REGISTRATION_ID -> provideGoogleUser(attributes);
            case NAVER_REGISTRATION_ID -> provideNaverUser(attributes);
            default -> throw new BusinessException(MSG_AUTH_OAUTH_UNSUPPORTED_REGISTRATION_PROVIDER, registrationId);
        };
    }

    private static OAuthUser provideGoogleUser(Map<String, Object> attributes) {
        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");

        return OAuthUser.of(GOOGLE_REGISTRATION_ID, name, email);
    }

    private static OAuthUser provideNaverUser(Map<String, Object> attributes) {
        Map<String, Object> naverAttributes = (Map<String, Object>) attributes.get("response");
        String name = (String) naverAttributes.get("name");
        String email = (String) naverAttributes.get("email");

        return OAuthUser.of(NAVER_REGISTRATION_ID, name, email);
    }
}
