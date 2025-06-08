package coz.weavon.core.user.application.repository;

import coz.weavon.core.user.application.model.command.UserSearchCommand;
import coz.weavon.core.user.domain.model.Users;
import java.util.List;

public interface UserRepository {

    Users findUsers(UserSearchCommand command);

    Users saveUsers(Users users);

    void updateUsers(Users users);

    void deleteUsers(List<Long> userIds);
}
