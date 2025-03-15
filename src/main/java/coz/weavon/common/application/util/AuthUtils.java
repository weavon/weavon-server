package coz.weavon.common.application.util;

import coz.weavon.exception.model.AuthException;
import coz.weavon.core.auth.domain.model.AuthUser;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class AuthUtils {

    private static final String MSG_AUTH_NONE = "message.authentication.none";

    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication) && authentication.getPrincipal() instanceof AuthUser authUser) {
            return authUser.getUsername();
        }

        throw new AuthException(MSG_AUTH_NONE);
    }
}
