package coz.weavon.context.member.application.service;

import coz.weavon.context.member.application.model.command.MemberOperateCommand;
import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import coz.weavon.context.member.application.model.result.MemberOperateResult;
import coz.weavon.context.member.domain.model.Member;
import coz.weavon.context.member.domain.model.Members;

public interface MemberService {
    Members searchMembers(MemberSearchCommand command);

    Member searchMember(MemberSearchCommand command);

    MemberOperateResult operateMembers(MemberOperateCommand command);
}
