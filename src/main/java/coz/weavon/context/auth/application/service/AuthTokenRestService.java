package coz.weavon.context.auth.application.service;

import coz.weavon.context.auth.domain.model.AuthToken;
import coz.weavon.context.auth.domain.model.AuthUser;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenRestService implements AuthTokenService {

    @Override
    public AuthToken getAuthTokenByAuthUser(AuthUser authUser) {
        return AuthToken.ofAuthUser(authUser);
    }
}
