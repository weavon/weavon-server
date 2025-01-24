package coz.weavon.context.member.infrastructure.model.condition;

import com.querydsl.core.types.dsl.BooleanExpression;
import coz.weavon.common.infrastructure.model.RestCondition;
import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import coz.weavon.context.member.infrastructure.model.entity.QMemberEntity;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Getter
@Builder
public class MemberSearchCondition extends RestCondition {

    private List<Long> memberIds;

    private String username;

    private final QMemberEntity member = QMemberEntity.memberEntity;

    public static MemberSearchCondition fromCommand(MemberSearchCommand command) {
        return MemberSearchCondition.builder()
                .memberIds(command.getMemberIds())
                .username(command.getUsername())
                .build();
    }

    @Override
    public boolean isInvalidCondition() {
        return this.memberIdsIsInvalid() && this.usernameIsInvalid();
    }

    public BooleanExpression inMemberIds() {
        if (this.memberIdsIsInvalid()) {
            return null;
        }

        return member.memberId.in(memberIds);
    }

    public BooleanExpression likeUsername() {
        if (this.usernameIsInvalid()) {
            return null;
        }

        return member.username.like(username);
    }

    private boolean memberIdsIsInvalid() {
        return CollectionUtils.isEmpty(memberIds);
    }

    private boolean usernameIsInvalid() {
        return !StringUtils.hasText(username);
    }
}
