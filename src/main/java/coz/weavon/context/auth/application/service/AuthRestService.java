package coz.weavon.context.auth.application.service;

import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.context.auth.application.adapter.AuthUserAdapter;
import coz.weavon.context.auth.domain.model.AuthUser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class AuthRestService implements AuthService {

    private static final String MSG_AUTH_USERNAME_EXISTS = "message.authentication.join.usernameExist";

    private static final String ROLE_USER = "USER";

    private final AuthUserAdapter userAdapter;

    @Override
    @Transactional
    public void saveAuthUser(String username, String password) {
        Optional<AuthUser> optionalAuthUser = userAdapter.findAuthUserByUsername(username);
        if (optionalAuthUser.isPresent()) {
            throw new BusinessException(MSG_AUTH_USERNAME_EXISTS);
        }

        AuthUser authUser = AuthUser.builder()
                .username(username)
                .password(password)
                .role(ROLE_USER)
                .build();

        userAdapter.saveAuthUser(authUser);
    }
}
