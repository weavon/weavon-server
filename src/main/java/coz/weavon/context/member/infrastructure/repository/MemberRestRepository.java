package coz.weavon.context.member.infrastructure.repository;

import coz.weavon.context.member.application.model.condition.MemberSearchCondition;
import coz.weavon.context.member.application.repository.MemberRepository;
import coz.weavon.context.member.domain.model.Member;
import coz.weavon.context.member.domain.model.Members;
import coz.weavon.context.member.infrastructure.model.MemberEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class MemberRestRepository implements MemberRepository {

    private final MemberQueryRepository queryRepository;
    private final MemberJpaRepository jpaRepository;

    @Override
    public Members findMembersByCondition(MemberSearchCondition condition) {
        condition.validate();
        List<MemberEntity> foundMemberEntities = queryRepository.findAllByCondition(condition);
        List<Member> foundMembers =
                foundMemberEntities.stream().map(MemberEntity::toDomain).toList();
        return Members.of(foundMembers);
    }
}
