package coz.weavon.context.member.application.repository;

import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import coz.weavon.context.member.domain.model.Members;

public interface MemberRepository {

    Members findMembersByCommand(MemberSearchCommand command);
}
