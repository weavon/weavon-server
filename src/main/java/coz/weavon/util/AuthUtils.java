package coz.weavon.util;

import coz.weavon.constant.Message;
import coz.weavon.exception.AuthException;
import coz.weavon.security.model.AuthUser;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class AuthUtils {

    public static AuthUser getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication) || !(authentication.getPrincipal() instanceof AuthUser)) {
            throw new AuthException(Message.Authentication.NOT_AUTHENTICATED);
        }

        return (AuthUser) authentication.getPrincipal();
    }

    public static String getUsername() {
        AuthUser authUser = getAuthUser();
        return authUser.getUsername();
    }
}
