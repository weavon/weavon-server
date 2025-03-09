package coz.weavon.core.user.infrastructure.repository;

import coz.weavon.core.user.application.model.condition.UserSearchCondition;
import coz.weavon.core.user.application.repository.UserRepository;
import coz.weavon.core.user.domain.model.User;
import coz.weavon.core.user.domain.model.Users;
import coz.weavon.core.user.infrastructure.model.UserEntity;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class UserRestRepository implements UserRepository {

    private final UserQueryRepository queryRepository;

    private final UserJpaRepository jpaRepository;

    @Override
    public Users findUsers(UserSearchCondition condition) {
        condition.validate();
        List<UserEntity> foundUserEntities = queryRepository.findAllByCondition(condition);
        List<User> foundUsers =
                foundUserEntities.stream().map(UserEntity::toDomain).toList();
        return Users.of(foundUsers);
    }

    @Override
    public Users saveUsers(Users users) {
        List<UserEntity> newUserEntities = UserEntity.fromDomains(users);
        List<UserEntity> savedUserEntities = jpaRepository.saveAll(newUserEntities);
        List<User> saveUsers =
                savedUserEntities.stream().map(UserEntity::toDomain).toList();
        return Users.of(saveUsers);
    }

    @Override
    public void updateUsers(Users users) {
        Map<Long, UserEntity> updateUserEntityMap = users.getValue().stream()
                .map(UserEntity::fromDomain)
                .collect(Collectors.toMap(UserEntity::getUserId, Function.identity()));

        List<Long> updateUserIds = users.getUserIds();
        List<UserEntity> foundUserEntities = jpaRepository.findAllById(updateUserIds);
        foundUserEntities.forEach(entity -> entity.update(updateUserEntityMap.get(entity.getUserId())));
    }

    @Override
    public void deleteUsers(List<Long> userIds) {
        jpaRepository.deleteAllById(userIds);
    }
}
