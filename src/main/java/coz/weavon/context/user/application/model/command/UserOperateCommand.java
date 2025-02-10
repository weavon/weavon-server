package coz.weavon.context.user.application.model.command;

import coz.weavon.common.application.model.command.RestCommand;
import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.context.user.domain.model.Users;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

@Getter
@Builder
public class UserOperateCommand extends RestCommand {

    private static final String MSG_USER_OPERATE_EMPTY = "message.user.operate.empty";

    private Users createTargetUsers;

    private Users updateTargetUsers;

    private List<Long> deleteTargetUserIds;

    public static UserOperateCommand ofCreateTargets(Users createTargetUsers) {
        return UserOperateCommand.builder().createTargetUsers(createTargetUsers).build();
    }

    public static UserOperateCommand ofUpdateTargets(Users updateTargetUsers) {
        return UserOperateCommand.builder().updateTargetUsers(updateTargetUsers).build();
    }

    public static UserOperateCommand ofDeleteTargets(List<Long> deleteTargetUserIds) {
        return UserOperateCommand.builder()
                .deleteTargetUserIds(deleteTargetUserIds)
                .build();
    }

    @Override
    public void validate() {
        if (!(this.hasUsersToCreate() || this.hasUsersToUpdate() || this.hasUsersToDelete())) {
            throw new BusinessException(MSG_USER_OPERATE_EMPTY);
        }
    }

    public boolean hasUsersToCreate() {
        return Objects.nonNull(createTargetUsers) && !createTargetUsers.isEmpty();
    }

    public boolean hasUsersToUpdate() {
        return Objects.nonNull(updateTargetUsers) && !updateTargetUsers.isEmpty();
    }

    public boolean hasUsersToDelete() {
        return !CollectionUtils.isEmpty(deleteTargetUserIds);
    }
}
