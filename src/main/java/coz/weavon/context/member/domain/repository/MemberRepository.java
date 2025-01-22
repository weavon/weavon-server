package coz.weavon.context.member.domain.repository;

import coz.weavon.context.member.domain.model.Members;
import coz.weavon.context.member.infrastructure.model.condition.MemberSearchCondition;
import java.util.Optional;

public interface MemberRepository {

    Optional<Members> findMembersByCondition(MemberSearchCondition condition);
}
