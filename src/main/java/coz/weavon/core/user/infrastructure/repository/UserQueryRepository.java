package coz.weavon.core.user.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import coz.weavon.core.user.application.model.condition.UserSearchCondition;
import coz.weavon.core.user.infrastructure.model.QUserEntity;
import coz.weavon.core.user.infrastructure.model.UserEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {

    private final JPAQueryFactory query;

    private final QUserEntity user = QUserEntity.userEntity;

    public List<UserEntity> findAllByCondition(UserSearchCondition condition) {
        return query.selectFrom(user)
                .where(condition.inUserIds(), condition.inUsernames(), condition.likeNickname(), condition.inEmails())
                .fetch();
    }
}
