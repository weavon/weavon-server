package coz.weavon.context.member.application.service;

import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import coz.weavon.context.member.application.repository.MemberRepository;
import coz.weavon.context.member.domain.model.Member;
import coz.weavon.context.member.domain.model.Members;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberRestService implements MemberService {

    private final MemberRepository repository;

    @Override
    public Members searchMembers(MemberSearchCommand command) {
        command.validate();
        return repository.findMembersByCommand(command);
    }

    @Override
    public Member searchMember(MemberSearchCommand command) {
        command.validate();
        Members members = repository.findMembersByCommand(command);
        return members.getSingleMember();
    }
}
