package coz.weavon.context.auth.application.service;

import coz.weavon.context.auth.application.adapter.AuthUserAdapter;
import coz.weavon.context.auth.domain.model.AuthUser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class AuthRestService implements AuthService {

    private final AuthUserAdapter userAdapter;

    @Override
    @Transactional(readOnly = true)
    public boolean checkUsernameExistence(String username) {
        Optional<AuthUser> optionalAuthUser = userAdapter.findAuthUserByUsername(username);
        return optionalAuthUser.isPresent();
    }
}
