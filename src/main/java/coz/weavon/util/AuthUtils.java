package coz.weavon.util;

import coz.weavon.common.exception.AuthException;
import coz.weavon.core.auth.domain.model.AuthUser;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class AuthUtils {

    private static final String MSG_AUTH_NONE = "message.authentication.none";

    public static AuthUser getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication) || !(authentication.getPrincipal() instanceof AuthUser)) {
            throw new AuthException(MSG_AUTH_NONE);
        }

        return (AuthUser) authentication.getPrincipal();
    }

    public static String getUsername() {
        AuthUser authUser = getAuthUser();
        return authUser.getUsername();
    }
}
