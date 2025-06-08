package coz.weavon.core.user.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import coz.weavon.core.shared.infrastructure.repository.RestQueryRepository;
import coz.weavon.core.user.infrastructure.model.QUserEntity;
import coz.weavon.core.user.infrastructure.model.UserEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository implements RestQueryRepository {

    private static final QUserEntity user = QUserEntity.userEntity;

    private final JPAQueryFactory queryFactory;

    public List<UserEntity> findUsersByCondition(
            List<Long> userIds, List<String> usernames, String nickname, List<String> emails) {
        JPAQuery<UserEntity> query = queryFactory
                .selectFrom(user)
                .where(
                        in(user.userId, userIds),
                        in(user.username, usernames),
                        like(user.nickname, nickname),
                        in(user.email, emails));

        return query.fetch();
    }
}
