package coz.weavon.core.auth.application.adapter;

import coz.weavon.core.auth.domain.model.AuthUser;
import coz.weavon.core.auth.domain.model.OAuthUser;
import java.util.Optional;

public interface AuthUserAdapter {

    Optional<AuthUser> findAuthUserByUsername(String username);

    AuthUser findAuthUserAndSaveOAuthUserIfAbsent(OAuthUser oAuthUser);
}
