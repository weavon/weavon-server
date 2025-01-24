package coz.weavon.context.member.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import coz.weavon.common.application.model.exception.BusinessException;
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

    public List<MemberEntity> findAllByCondition(MemberSearchCondition condition) {
        if (condition.isInvalidCondition()) {
            throw new BusinessException(MSG_VLD_INVLD_SEARCH_CONDITION);
        }

        QMemberEntity member = QMemberEntity.memberEntity;

        return query.selectFrom(member)
                .where(member.memberId.in(condition.getMemberIds()), member.username.like(condition.getUsername()))
                .fetch();
    }
}
