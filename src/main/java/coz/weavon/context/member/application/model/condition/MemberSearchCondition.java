package coz.weavon.context.member.application.model.condition;

import com.querydsl.core.types.dsl.BooleanExpression;
import coz.weavon.common.application.model.condition.RestCondition;
import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import coz.weavon.context.member.infrastructure.model.QMemberEntity;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSearchCondition extends RestCondition {

    private static final String MSG_VLD_INVLD_SEARCH_CONDITION = "message.validation.invalid.searchCondition";

    private final QMemberEntity member = QMemberEntity.memberEntity;

    private List<Long> memberIds;

    private List<String> usernames;

    private String nickname;

    private List<String> emails;

    public static MemberSearchCondition fromCommand(MemberSearchCommand command) {
        return MemberSearchCondition.builder()
                .memberIds(command.getMemberIds())
                .usernames(command.getUsernames())
                .nickname(command.getNickname())
                .emails(command.getEmails())
                .build();
    }

    @Override
    public void validate() {
        if (super.isInvalidInCondition(memberIds)
                && super.isInvalidInCondition(usernames)
                && super.isInvalidLikeCondition(nickname)
                && super.isInvalidInCondition(emails)) {
            throw new BusinessException(MSG_VLD_INVLD_SEARCH_CONDITION);
        }
    }

    public BooleanExpression inMemberIds() {
        if (super.isInvalidInCondition(memberIds)) {
            return null;
        }

        return member.memberId.in(memberIds);
    }

    public BooleanExpression inUsernames() {
        if (super.isInvalidInCondition(usernames)) {
            return null;
        }

        return member.username.in(usernames);
    }

    public BooleanExpression likeNickname() {
        if (super.isInvalidLikeCondition(nickname)) {
            return null;
        }

        return member.nickname.like("%" + nickname + "%");
    }

    public BooleanExpression inEmails() {
        if (super.isInvalidInCondition(emails)) {
            return null;
        }

        return member.email.in(emails);
    }
}
