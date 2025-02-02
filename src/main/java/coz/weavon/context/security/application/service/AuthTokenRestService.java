package coz.weavon.context.security.application.service;

import coz.weavon.context.security.domain.model.AuthToken;
import coz.weavon.context.security.domain.model.AuthUser;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenRestService implements AuthTokenService {

    @Override
    public AuthToken getAuthTokenByAuthUser(AuthUser authUser) {
        return AuthToken.ofAuthUser(authUser);
    }
}
