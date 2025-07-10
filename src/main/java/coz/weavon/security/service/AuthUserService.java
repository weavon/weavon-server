package coz.weavon.security.service;

import coz.weavon.constant.Message;
import coz.weavon.core.user.application.model.command.UserOperateCommand;
import coz.weavon.core.user.application.model.command.UserSearchCommand;
import coz.weavon.core.user.application.model.result.UserOperateResult;
import coz.weavon.core.user.application.service.UserService;
import coz.weavon.core.user.domain.model.User;
import coz.weavon.core.user.domain.model.Users;
import coz.weavon.helper.MessageTranslator;
import coz.weavon.security.model.AuthUser;
import coz.weavon.security.model.OAuthUser;
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

    private final MessageTranslator messageTranslator;

    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSearchCommand userSearchCommand = UserSearchCommand.ofUsername(username);
        Optional<User> foundUser = userService.searchOptionalUser(userSearchCommand);

        if (foundUser.isPresent()) {
            User user = foundUser.get();
            return AuthUser.ofUser(user);
        }

        throw new UsernameNotFoundException(messageTranslator.translate(Message.Authentication.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        String registrationId = request.getClientRegistration().getRegistrationId();
        OAuth2User oAuth2User = super.loadUser(request);
        OAuthUser oAuthUser = OAuthUser.ofAttributes(registrationId, oAuth2User.getAttributes());

        UserSearchCommand userSearchCommand = UserSearchCommand.ofEmail(oAuthUser.getEmail());
        Optional<User> foundUser = userService.searchOptionalUser(userSearchCommand);

        if (foundUser.isPresent()) {
            User user = foundUser.get();
            return AuthUser.ofUser(user);
        }

        User createTargetUser = User.ofUser(oAuthUser.getEmail(), oAuthUser.getNickname(), oAuthUser.getEmail());
        UserOperateCommand userOperateCommand = UserOperateCommand.ofCreateTargets(Users.of(createTargetUser));
        UserOperateResult operateResult = userService.operateUsers(userOperateCommand);
        User createdUser = operateResult
                .getCreatedUsers()
                .getUserByEmail(oAuthUser.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(
                        messageTranslator.translate(Message.Authentication.USER_NOT_FOUND)));

        return AuthUser.ofUser(createdUser);
    }
}
