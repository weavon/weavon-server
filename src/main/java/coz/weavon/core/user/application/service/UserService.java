package coz.weavon.core.user.application.service;

import coz.weavon.core.user.application.model.command.UserOperateCommand;
import coz.weavon.core.user.application.model.command.UserSearchCommand;
import coz.weavon.core.user.application.model.result.UserOperateResult;
import coz.weavon.core.user.domain.model.User;
import coz.weavon.core.user.domain.model.Users;
import java.util.Optional;

public interface UserService {

    Users searchUsers(UserSearchCommand command);

    User searchUser(UserSearchCommand command);

    Optional<User> searchOptionalUser(UserSearchCommand command);

    UserOperateResult operateUsers(UserOperateCommand command);
}
