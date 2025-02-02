package coz.weavon.context.security.infrastructure.adapter;

import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.common.infrastructure.model.Adapter;
import coz.weavon.context.member.application.model.command.MemberOperateCommand;
import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import coz.weavon.context.member.application.model.result.MemberOperateResult;
import coz.weavon.context.member.application.service.MemberService;
import coz.weavon.context.member.domain.model.Member;
import coz.weavon.context.member.domain.model.Members;
import coz.weavon.context.security.application.adapter.SecurityMemberAdapter;
import coz.weavon.context.security.domain.model.AuthUser;
import coz.weavon.context.security.domain.model.OAuthUser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
class SecurityMemberRestAdapter implements SecurityMemberAdapter {

    private static final String MSG_AUTH_FAIL_SIGN_UP = "message.authentication.failed.signUp";

    private final MemberService memberService;

    @Override
    public AuthUser findAuthUserAndSaveOAuthUserIfAbsent(OAuthUser oAuthUser) {
        Optional<Member> optionalSearchedMember = this.searchOAuthUser(oAuthUser);
        if (optionalSearchedMember.isPresent()) {
            Member searchedMember = optionalSearchedMember.get();
            return AuthUser.of(searchedMember.getUsername(), searchedMember.getEmail(), searchedMember.getRoleName());
        }

        return this.saveOAuthUser(oAuthUser);
    }

    private Optional<Member> searchOAuthUser(OAuthUser oAuthUser) {
        MemberSearchCommand searchCommand = MemberSearchCommand.ofEmail(oAuthUser.getEmail());
        Members searchedMembers = memberService.searchMembers(searchCommand);
        return searchedMembers.getMemberByEmail(oAuthUser.getEmail());
    }

    private AuthUser saveOAuthUser(OAuthUser oAuthUser) {
        Member createTargetMember = Member.ofUser(oAuthUser.getEmail(), oAuthUser.getNickname(), oAuthUser.getEmail());
        MemberOperateCommand operateCommand = MemberOperateCommand.ofCreateTargets(Members.of(createTargetMember));
        MemberOperateResult operateResult = memberService.operateMembers(operateCommand);
        Member createdMember = operateResult
                .getCreatedMembers()
                .getMemberByEmail(oAuthUser.getEmail())
                .orElseThrow(() -> new BusinessException(MSG_AUTH_FAIL_SIGN_UP));
        return AuthUser.of(createdMember.getUsername(), createdMember.getEmail(), createdMember.getRoleName());
    }
}
