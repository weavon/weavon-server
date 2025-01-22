package coz.weavon.context.member.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import coz.weavon.context.member.infrastructure.model.condition.MemberSearchCondition;
import coz.weavon.context.member.infrastructure.model.entity.MemberEntity;
import coz.weavon.context.member.infrastructure.model.entity.QMemberEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory query;

    public List<MemberEntity> findAllByCondition(MemberSearchCondition condition) {
        QMemberEntity member = QMemberEntity.memberEntity;

        return query.selectFrom(member)
                .where(member.memberId.in(condition.getMemberIds()))
                .fetch();
    }
}
