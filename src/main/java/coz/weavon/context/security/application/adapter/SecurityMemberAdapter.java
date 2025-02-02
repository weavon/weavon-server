package coz.weavon.context.security.application.adapter;

import coz.weavon.context.security.domain.model.AuthUser;
import coz.weavon.context.security.domain.model.OAuthUser;

public interface SecurityMemberAdapter {

    AuthUser findAuthUserAndSaveOAuthUserIfAbsent(OAuthUser oAuthUser);
}
