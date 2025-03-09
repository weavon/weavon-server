package coz.weavon.core.user.application.model.condition;

import com.querydsl.core.types.dsl.BooleanExpression;
import coz.weavon.common.application.model.condition.RestCondition;
import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.core.user.application.model.command.UserSearchCommand;
import coz.weavon.core.user.infrastructure.model.QUserEntity;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSearchCondition extends RestCondition {

    private static final String MSG_VLD_INVLD_SEARCH_CONDITION = "message.validation.invalid.searchCondition";

    private final QUserEntity user = QUserEntity.userEntity;

    private List<Long> userIds;

    private List<String> usernames;

    private String nickname;

    private List<String> emails;

    public static UserSearchCondition fromCommand(UserSearchCommand command) {
        return UserSearchCondition.builder()
                .userIds(command.getUserIds())
                .usernames(command.getUsernames())
                .nickname(command.getNickname())
                .emails(command.getEmails())
                .build();
    }

    @Override
    public void validate() {
        if (super.isInvalidInCondition(userIds)
                && super.isInvalidInCondition(usernames)
                && super.isInvalidLikeCondition(nickname)
                && super.isInvalidInCondition(emails)) {
            throw new BusinessException(MSG_VLD_INVLD_SEARCH_CONDITION);
        }
    }

    public BooleanExpression inUserIds() {
        if (super.isInvalidInCondition(userIds)) {
            return null;
        }

        return user.userId.in(userIds);
    }

    public BooleanExpression inUsernames() {
        if (super.isInvalidInCondition(usernames)) {
            return null;
        }

        return user.username.in(usernames);
    }

    public BooleanExpression likeNickname() {
        if (super.isInvalidLikeCondition(nickname)) {
            return null;
        }

        return user.nickname.like("%" + nickname + "%");
    }

    public BooleanExpression inEmails() {
        if (super.isInvalidInCondition(emails)) {
            return null;
        }

        return user.email.in(emails);
    }
}
