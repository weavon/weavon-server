package coz.weavon.core.auth.infrastructure.adapter;

import coz.weavon.exception.model.BusinessException;
import coz.weavon.core.shared.model.Adapter;
import coz.weavon.core.auth.application.adapter.AuthUserAdapter;
import coz.weavon.core.auth.domain.model.AuthUser;
import coz.weavon.core.auth.domain.model.OAuthUser;
import coz.weavon.core.user.application.model.command.UserOperateCommand;
import coz.weavon.core.user.application.model.command.UserSearchCommand;
import coz.weavon.core.user.application.model.result.UserOperateResult;
import coz.weavon.core.user.application.service.UserService;
import coz.weavon.core.user.domain.model.Role;
import coz.weavon.core.user.domain.model.User;
import coz.weavon.core.user.domain.model.Users;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
class AuthUserRestAdapter implements AuthUserAdapter {

    private static final String MSG_AUTH_FAIL_SIGN_UP = "message.authentication.failed.signUp";

    private final UserService userService;

    @Override
    public Optional<AuthUser> findAuthUserByUsername(String username) {
        UserSearchCommand searchCommand = UserSearchCommand.ofUsername(username);
        Users foundUsers = userService.searchUsers(searchCommand);
        return foundUsers
                .getUserByUsername(username)
                .map(user -> AuthUser.of(user.getUsername(), user.getPassword(), user.getRoleName()));
    }

    @Override
    public AuthUser findAuthUserAndSaveOAuthUserIfAbsent(OAuthUser oAuthUser) {
        UserSearchCommand searchCommand = UserSearchCommand.ofEmail(oAuthUser.getEmail());
        Users foundUsers = userService.searchUsers(searchCommand);
        Optional<User> optionalFoundUser = foundUsers.getUserByEmail(oAuthUser.getEmail());
        if (optionalFoundUser.isPresent()) {
            User foundUSer = optionalFoundUser.get();
            return AuthUser.of(foundUSer.getUsername(), foundUSer.getPassword(), foundUSer.getRoleName());
        }

        User createTargetUser = User.ofUser(oAuthUser.getEmail(), oAuthUser.getNickname(), oAuthUser.getEmail());
        UserOperateCommand operateCommand = UserOperateCommand.ofCreateTargets(Users.of(createTargetUser));
        UserOperateResult operateResult = userService.operateUsers(operateCommand);
        User createdUser = operateResult
                .getCreatedUsers()
                .getUserByEmail(oAuthUser.getEmail())
                .orElseThrow(() -> new BusinessException(MSG_AUTH_FAIL_SIGN_UP));
        return AuthUser.of(createdUser.getUsername(), createdUser.getPassword(), createdUser.getRoleName());
    }

    @Override
    public void saveAuthUser(AuthUser authUser) {
        User user = User.builder()
                .username(authUser.getUsername())
                .password(authUser.getPassword())
                .role(Role.USER)
                .nickname(authUser.getUsername())
                .build();

        UserOperateCommand operateCommand = UserOperateCommand.ofCreateTargets(Users.of(user));
        userService.operateUsers(operateCommand);
    }
}
