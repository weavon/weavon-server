package coz.weavon.core.user.application.repository;

import coz.weavon.core.user.application.model.condition.UserSearchCondition;
import coz.weavon.core.user.domain.model.Users;
import java.util.List;

public interface UserRepository {

    Users findUsers(UserSearchCondition condition);

    Users saveUsers(Users users);

    void updateUsers(Users users);

    void deleteUsers(List<Long> userIds);
}
