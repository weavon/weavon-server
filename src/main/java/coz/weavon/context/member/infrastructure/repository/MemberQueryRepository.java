package coz.weavon.context.member.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import coz.weavon.context.member.application.model.condition.MemberSearchCondition;
import coz.weavon.context.member.infrastructure.model.MemberEntity;
import coz.weavon.context.member.infrastructure.model.QMemberEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory query;

    private final QMemberEntity member = QMemberEntity.memberEntity;

    public List<MemberEntity> findAllByCondition(MemberSearchCondition condition) {
        return query.selectFrom(member)
                .where(condition.inMemberIds(), condition.inUsernames(), condition.likeNickname(), condition.inEmails())
                .fetch();
    }
}
