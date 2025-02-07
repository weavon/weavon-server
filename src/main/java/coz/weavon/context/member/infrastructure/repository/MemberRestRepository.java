package coz.weavon.context.member.infrastructure.repository;

import coz.weavon.context.member.application.model.condition.MemberSearchCondition;
import coz.weavon.context.member.application.repository.MemberRepository;
import coz.weavon.context.member.domain.model.Member;
import coz.weavon.context.member.domain.model.Members;
import coz.weavon.context.member.infrastructure.model.MemberEntity;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class MemberRestRepository implements MemberRepository {

    private final MemberQueryRepository queryRepository;

    private final MemberJpaRepository jpaRepository;

    @Override
    public Members findMembers(MemberSearchCondition condition) {
        condition.validate();
        List<MemberEntity> foundMemberEntities = queryRepository.findAllByCondition(condition);
        List<Member> foundMembers =
                foundMemberEntities.stream().map(MemberEntity::toDomain).toList();
        return Members.of(foundMembers);
    }

    @Override
    public Members saveMembers(Members members) {
        List<MemberEntity> newMemberEntities = MemberEntity.fromDomains(members);
        List<MemberEntity> savedMembersEntities = jpaRepository.saveAll(newMemberEntities);
        List<Member> savedMembers =
                savedMembersEntities.stream().map(MemberEntity::toDomain).toList();
        return Members.of(savedMembers);
    }

    @Override
    public void updateMembers(Members members) {
        Map<Long, MemberEntity> updateMemberEntityMap = members.getValue().stream()
                .map(MemberEntity::fromDomain)
                .collect(Collectors.toMap(MemberEntity::getMemberId, Function.identity()));

        List<Long> updateMemberIds = members.getMemberIds();
        List<MemberEntity> foundMemberEntities = jpaRepository.findAllById(updateMemberIds);
        foundMemberEntities.forEach(entity -> entity.update(updateMemberEntityMap.get(entity.getMemberId())));
    }

    @Override
    public void deleteMembers(List<Long> memberIds) {
        jpaRepository.deleteAllById(memberIds);
    }
}
