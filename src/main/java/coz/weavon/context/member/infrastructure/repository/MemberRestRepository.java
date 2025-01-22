package coz.weavon.context.member.infrastructure.repository;

import coz.weavon.common.application.model.exception.BusinessException;
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
public class MemberRestRepository implements MemberRepository {

    private static final String MSG_VLD_INVLD_SEARCH_CONDITION = "message.validation.invalid.searchCondition";

    private final MemberQueryRepository queryRepository;

    @Override
    public Members findMembersByCommand(MemberSearchCommand command) {
        MemberSearchCondition condition = MemberSearchCondition.fromCommand(command);

        if (condition.isInvalidCondition()) {
            throw new BusinessException(MSG_VLD_INVLD_SEARCH_CONDITION);
        }

        List<MemberEntity> entities = queryRepository.findAllByCondition(condition);
        List<Member> domains = entities.stream().map(MemberEntity::toDomain).toList();

        return Members.of(domains);
    }
}
