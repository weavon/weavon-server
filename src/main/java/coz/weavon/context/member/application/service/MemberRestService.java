package coz.weavon.context.member.application.service;

import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import coz.weavon.context.member.application.model.condition.MemberSearchCondition;
import coz.weavon.context.member.application.repository.MemberRepository;
import coz.weavon.context.member.domain.model.Member;
import coz.weavon.context.member.domain.model.Members;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class MemberRestService implements MemberService {

    private static final String MSG_VLD_INVLD_SINGLE_RESULT = "message.validation.invalid.singleResult";

    private static final String LBL_MEMBER = "label.member";

    private final MemberRepository repository;

    @Override
    public Members searchMembers(MemberSearchCommand command) {
        command.validate();
        return repository.findMembersByCondition(MemberSearchCondition.fromCommand(command));
    }

    @Override
    public Member searchMember(MemberSearchCommand command) {
        command.validate();

        Members members = repository.findMembersByCondition(MemberSearchCondition.fromCommand(command));
        Optional<Member> member = members.getSingleMember();
        if (member.isPresent()) {
            return member.get();
        }

        throw new BusinessException(MSG_VLD_INVLD_SINGLE_RESULT, LBL_MEMBER);
    }
}
