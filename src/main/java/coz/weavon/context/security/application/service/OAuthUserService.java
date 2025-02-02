package coz.weavon.context.security.application.service;

import coz.weavon.context.security.application.adapter.SecurityMemberAdapter;
import coz.weavon.context.security.domain.model.OAuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuthUserService extends DefaultOAuth2UserService {

    private final SecurityMemberAdapter memberAdapter;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        final String registrationId = request.getClientRegistration().getRegistrationId();
        final OAuth2User oAuth2User = super.loadUser(request);
        final OAuthUser oAuthUser = OAuthUser.ofAttributes(registrationId, oAuth2User.getAttributes());

        return memberAdapter.findAuthUserAndSaveOAuthUserIfAbsent(oAuthUser);
    }
}
