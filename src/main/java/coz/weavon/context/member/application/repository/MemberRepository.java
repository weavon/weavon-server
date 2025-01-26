package coz.weavon.context.member.application.repository;

import coz.weavon.context.member.application.model.condition.MemberSearchCondition;
import coz.weavon.context.member.domain.model.Members;

public interface MemberRepository {

    Members findMembers(MemberSearchCondition condition);

    Members saveMembers(Members members);
}
