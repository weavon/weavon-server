package coz.weavon.context.member.application.repository;

import coz.weavon.context.member.application.model.condition.MemberSearchCondition;
import coz.weavon.context.member.domain.model.Members;
import java.util.List;

public interface MemberRepository {

    Members findMembers(MemberSearchCondition condition);

    Members saveMembers(Members members);

    void updateMembers(Members members);

    void deleteMembers(List<Long> memberIds);
}
