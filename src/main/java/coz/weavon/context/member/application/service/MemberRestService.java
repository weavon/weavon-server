package coz.weavon.context.member.application.service;

import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.context.member.application.model.command.MemberOperateCommand;
import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import coz.weavon.context.member.application.model.condition.MemberSearchCondition;
import coz.weavon.context.member.application.model.result.MemberOperateResult;
import coz.weavon.context.member.application.repository.MemberRepository;
import coz.weavon.context.member.domain.model.Member;
import coz.weavon.context.member.domain.model.Members;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class MemberRestService implements MemberService {

    private static final String MSG_VLD_INVLD_SINGLE_RESULT = "message.validation.invalid.singleResult";

    private static final String LBL_MEMBER = "label.member";

    private final MemberRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Members searchMembers(MemberSearchCommand command) {
        command.validate();

        return repository.findMembers(MemberSearchCondition.fromCommand(command));
    }

    @Override
    @Transactional(readOnly = true)
    public Member searchMember(MemberSearchCommand command) {
        command.validate();

        Members members = repository.findMembers(MemberSearchCondition.fromCommand(command));
        Optional<Member> member = members.getSingleMember();
        if (member.isPresent()) {
            return member.get();
        }

        throw new BusinessException(MSG_VLD_INVLD_SINGLE_RESULT, LBL_MEMBER);
    }

    @Override
    @Transactional
    public MemberOperateResult operateMembers(MemberOperateCommand command) {
        command.validate();

        MemberOperateResult result = new MemberOperateResult();

        if (command.hasMembersToDelete()) {
            repository.deleteMembers(command.getDeleteTargetMemberIds());
        }

        if (command.hasMembersToUpdate()) {
            repository.updateMembers(command.getUpdateTargetMembers());
            result.setUpdatedMembers(command.getUpdateTargetMembers());
        }

        if (command.hasMembersToCreate()) {
            Members savedMembers = repository.saveMembers(command.getCreateTargetMembers());
            result.setCreatedMembers(savedMembers);
        }

        return result;
    }
}
