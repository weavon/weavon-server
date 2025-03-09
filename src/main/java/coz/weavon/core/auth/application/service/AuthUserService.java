package coz.weavon.core.auth.application.service;

import coz.weavon.common.application.service.MessageTranslator;
import coz.weavon.core.auth.application.adapter.AuthUserAdapter;
import coz.weavon.core.auth.domain.model.AuthUser;
import coz.weavon.core.auth.domain.model.OAuthUser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthUserService extends DefaultOAuth2UserService implements UserDetailsService {

    private static final String MSG_AUTH_USER_NOT_FOUND = "message.authentication.user.notFound";

    private final MessageTranslator messageTranslator;

    private final AuthUserAdapter userAdapter;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthUser> optionalAuthUser = userAdapter.findAuthUserByUsername(username);
        if (optionalAuthUser.isPresent()) {
            return optionalAuthUser.get();
        }

        throw new UsernameNotFoundException(messageTranslator.translate(MSG_AUTH_USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        String registrationId = request.getClientRegistration().getRegistrationId();
        OAuth2User oAuth2User = super.loadUser(request);
        OAuthUser oAuthUser = OAuthUser.ofAttributes(registrationId, oAuth2User.getAttributes());

        return userAdapter.findAuthUserAndSaveOAuthUserIfAbsent(oAuthUser);
    }
}
