package coz.weavon.context.user.application.service;

import coz.weavon.context.user.application.model.command.UserOperateCommand;
import coz.weavon.context.user.application.model.command.UserSearchCommand;
import coz.weavon.context.user.application.model.result.UserOperateResult;
import coz.weavon.context.user.domain.model.User;
import coz.weavon.context.user.domain.model.Users;
import java.util.Optional;

public interface UserService {

    Users searchUsers(UserSearchCommand command);

    User searchUser(UserSearchCommand command);

    Optional<User> searchOptionalUser(UserSearchCommand command);

    UserOperateResult operateUsers(UserOperateCommand command);
}
