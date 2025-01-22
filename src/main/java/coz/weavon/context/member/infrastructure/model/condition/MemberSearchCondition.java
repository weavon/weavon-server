package coz.weavon.context.member.infrastructure.model.condition;

import coz.weavon.common.infrastructure.model.RestCondition;
import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSearchCondition extends RestCondition {

    private List<Long> memberIds;

    private String username;

    public static MemberSearchCondition fromCommand(MemberSearchCommand command) {
        return MemberSearchCondition.builder()
                .memberIds(command.getMemberIds())
                .username(command.getUsername())
                .build();
    }

    @Override
    public boolean isInvalidCondition() {
        if (Objects.nonNull(memberIds)) {
            return false;
        }

        if (Objects.nonNull(username)) {
            return false;
        }

        return true;
    }
}
