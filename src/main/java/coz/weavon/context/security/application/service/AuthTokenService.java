package coz.weavon.context.security.application.service;

import coz.weavon.context.security.domain.model.AuthToken;
import coz.weavon.context.security.domain.model.AuthUser;

public interface AuthTokenService {
    AuthToken getAuthTokenByAuthUser(AuthUser authUser);
}
