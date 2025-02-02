package coz.weavon.context.security.domain.model;

import coz.weavon.common.application.model.exception.BusinessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegistrationProvider {
    GOOGLE("google"),
    NAVER("naver");

    private static final String MSG_AUTH_OAUTH_UNSUPPORTED_REGISTRATION_PROVIDER =
            "message.authentication.oauth.unsupportedRegistrationProvider";

    private final String registrationId;

    public static RegistrationProvider of(String registrationId) {
        for (RegistrationProvider provider : values()) {
            if (provider.registrationId.equals(registrationId)) {
                return provider;
            }
        }

        throw new BusinessException(MSG_AUTH_OAUTH_UNSUPPORTED_REGISTRATION_PROVIDER, registrationId);
    }
}
