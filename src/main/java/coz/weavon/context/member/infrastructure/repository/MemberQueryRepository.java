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

    private static final String MSG_VLD_INVLD_SEARCH_CONDITION = "message.validation.invalid.searchCondition";

    private final JPAQueryFactory query;

    private final QMemberEntity member = QMemberEntity.memberEntity;

    public List<MemberEntity> findAllByCondition(MemberSearchCondition condition) {
        condition.validate();

        return query.selectFrom(member)
                .where(condition.inMemberIds(), condition.inUsernames(), condition.likeNickname(), condition.equalEmail())
                .fetch();
    }
}
