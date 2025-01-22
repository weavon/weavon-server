package coz.weavon.context.member.infrastructure.repository;

import coz.weavon.context.member.domain.model.Member;
import coz.weavon.context.member.domain.model.Members;
import coz.weavon.context.member.domain.repository.MemberRepository;
import coz.weavon.context.member.infrastructure.model.condition.MemberSearchCondition;
import coz.weavon.context.member.infrastructure.model.entity.MemberEntity;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRestRepository implements MemberRepository {

    private final MemberQueryRepository queryRepository;

    @Override
    public Optional<Members> findMembersByCondition(MemberSearchCondition condition) {
        if (condition.isInvalidCondition()) {
            return Optional.empty();
        }

        List<MemberEntity> entities = queryRepository.findAllByCondition(condition);
        List<Member> domains = entities.stream().map(MemberEntity::toDomain).toList();

        return Optional.of(Members.of(domains));
    }
}
