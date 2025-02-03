package coz.weavon.context.auth.application.adapter;

import coz.weavon.context.auth.domain.model.AuthUser;
import coz.weavon.context.auth.domain.model.OAuthUser;

public interface AuthMemberAdapter {

    AuthUser findAuthUserAndSaveOAuthUserIfAbsent(OAuthUser oAuthUser);
}
