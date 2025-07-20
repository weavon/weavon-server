package coz.weavon.core.user.application.model.command;

import coz.weavon.common.command.Command;
import coz.weavon.constant.Message;
import coz.weavon.core.user.domain.model.Users;
import coz.weavon.exception.BusinessException;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

@Getter
@SuperBuilder
public class UserOperateCommand extends Command {

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
            throw new BusinessException(Message.User.NO_OPERATION_USER_TARGET);
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
