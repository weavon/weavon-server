package coz.weavon.context.member.infrastructure.repository;

import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import coz.weavon.context.member.application.repository.MemberRepository;
import coz.weavon.context.member.domain.model.Member;
import coz.weavon.context.member.domain.model.Members;
import coz.weavon.context.member.infrastructure.model.condition.MemberSearchCondition;
import coz.weavon.context.member.infrastructure.model.entity.MemberEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class MemberRestRepository implements MemberRepository {

    private final MemberQueryRepository queryRepository;

    @Override
    public Members findMembersByCommand(MemberSearchCommand command) {
        MemberSearchCondition condition = MemberSearchCondition.fromCommand(command);
        List<MemberEntity> entities = queryRepository.findAllByCondition(condition);
        List<Member> domains = entities.stream().map(MemberEntity::toDomain).toList();

        return Members.of(domains);
    }
}
