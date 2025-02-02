package coz.weavon.context.security.domain.service;

import coz.weavon.context.security.domain.model.OAuthUser;
import coz.weavon.context.security.domain.model.RegistrationProvider;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OAuthUserConstructor {

    public static OAuthUser constructOAuthUser(String registrationId, Map<String, Object> attributes) {
        final RegistrationProvider registrationProvider = RegistrationProvider.of(registrationId);
        return switch (registrationProvider) {
            case GOOGLE -> constructGoogleOAuthUser(attributes);
            case NAVER -> constructNaverOAuthUser(attributes);
        };
    }

    private static OAuthUser constructGoogleOAuthUser(Map<String, Object> attributes) {
        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");

        return OAuthUser.of(RegistrationProvider.GOOGLE, name, email);
    }

    private static OAuthUser constructNaverOAuthUser(Map<String, Object> attributes) {
        Map<String, Object> naverAttributes = (Map<String, Object>) attributes.get("response");
        String name = (String) naverAttributes.get("name");
        String email = (String) naverAttributes.get("email");

        return OAuthUser.of(RegistrationProvider.NAVER, name, email);
    }
}
