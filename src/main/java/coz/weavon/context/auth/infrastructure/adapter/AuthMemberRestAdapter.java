package coz.weavon.context.auth.infrastructure.adapter;

import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.common.infrastructure.model.Adapter;
import coz.weavon.context.auth.application.adapter.AuthMemberAdapter;
import coz.weavon.context.auth.domain.model.AuthUser;
import coz.weavon.context.auth.domain.model.OAuthUser;
import coz.weavon.context.member.application.model.command.MemberOperateCommand;
import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import coz.weavon.context.member.application.model.result.MemberOperateResult;
import coz.weavon.context.member.application.service.MemberService;
import coz.weavon.context.member.domain.model.Member;
import coz.weavon.context.member.domain.model.Members;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
class AuthMemberRestAdapter implements AuthMemberAdapter {

    private static final String MSG_AUTH_FAIL_SIGN_UP = "message.authentication.failed.signUp";

    private final MemberService memberService;

    @Override
    public AuthUser findAuthUserAndSaveOAuthUserIfAbsent(OAuthUser oAuthUser) {
        MemberSearchCommand searchCommand = MemberSearchCommand.ofEmail(oAuthUser.getEmail());
        Members searchedMembers = memberService.searchMembers(searchCommand);
        Optional<Member> optionalSearchedMember = searchedMembers.getMemberByEmail(oAuthUser.getEmail());
        if (optionalSearchedMember.isPresent()) {
            Member searchedMember = optionalSearchedMember.get();
            return AuthUser.of(searchedMember.getUsername(), searchedMember.getRoleName());
        }

        Member createTargetMember = Member.ofUser(oAuthUser.getEmail(), oAuthUser.getNickname(), oAuthUser.getEmail());
        MemberOperateCommand operateCommand = MemberOperateCommand.ofCreateTargets(Members.of(createTargetMember));
        MemberOperateResult operateResult = memberService.operateMembers(operateCommand);
        Member createdMember = operateResult
                .getCreatedMembers()
                .getMemberByEmail(oAuthUser.getEmail())
                .orElseThrow(() -> new BusinessException(MSG_AUTH_FAIL_SIGN_UP));
        return AuthUser.of(createdMember.getUsername(), createdMember.getRoleName());
    }
}
