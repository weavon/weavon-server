package coz.weavon.context.auth.application.adapter;

import coz.weavon.context.auth.domain.model.AuthUser;
import coz.weavon.context.auth.domain.model.OAuthUser;
import java.util.Optional;

public interface AuthUserAdapter {

    Optional<AuthUser> findAuthUserByUsername(String username);

    AuthUser findAuthUserAndSaveOAuthUserIfAbsent(OAuthUser oAuthUser);
}
