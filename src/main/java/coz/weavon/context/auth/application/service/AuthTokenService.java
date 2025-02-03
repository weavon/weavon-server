package coz.weavon.context.auth.application.service;

import coz.weavon.context.auth.domain.model.AuthToken;
import coz.weavon.context.auth.domain.model.AuthUser;

public interface AuthTokenService {
    AuthToken getAuthTokenByAuthUser(AuthUser authUser);
}
