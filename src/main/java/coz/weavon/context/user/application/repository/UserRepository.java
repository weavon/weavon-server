package coz.weavon.context.user.application.repository;

import coz.weavon.context.user.application.model.condition.UserSearchCondition;
import coz.weavon.context.user.domain.model.Users;
import java.util.List;

public interface UserRepository {

    Users findUsers(UserSearchCondition condition);

    Users saveUsers(Users users);

    void updateUsers(Users users);

    void deleteUsers(List<Long> userIds);
}
