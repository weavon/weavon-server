package coz.weavon.context.member.application.service;

import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import coz.weavon.context.member.domain.model.Members;
import coz.weavon.context.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberRestService implements MemberService {

    private final MemberRepository repository;

    public Members searchMembers(MemberSearchCommand command) {
        return repository.findMembersByCondition(null).orElseThrow();
    }
}
