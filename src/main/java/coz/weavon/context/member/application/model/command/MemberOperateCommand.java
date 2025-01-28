package coz.weavon.context.member.application.model.command;

import coz.weavon.common.application.model.command.RestCommand;
import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.context.member.domain.model.Members;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

@Getter
@Builder
public class MemberOperateCommand extends RestCommand {

    private Members createTargetMembers;

    private Members updateTargetMembers;

    private List<Long> deleteTargetMemberIds;

    public static MemberOperateCommand ofCreateTargets(Members createTargetMembers) {
        return MemberOperateCommand.builder()
                .createTargetMembers(createTargetMembers)
                .build();
    }

    public static MemberOperateCommand ofUpdateTargets(Members updateTargetMembers) {
        return MemberOperateCommand.builder()
                .updateTargetMembers(updateTargetMembers)
                .build();
    }

    public static MemberOperateCommand ofDeleteTargets(List<Long> deleteTargetMemberIds) {
        return MemberOperateCommand.builder()
                .deleteTargetMemberIds(deleteTargetMemberIds)
                .build();
    }

    @Override
    public void validate() {
        if (!(this.hasMembersToCreate() || this.hasMembersToUpdate() || this.hasMembersToDelete())) {
            throw new BusinessException("There are no members to operate.");
        }
    }

    public boolean hasMembersToCreate() {
        return Objects.nonNull(createTargetMembers) && !createTargetMembers.isEmpty();
    }

    public boolean hasMembersToUpdate() {
        return Objects.nonNull(updateTargetMembers) && !updateTargetMembers.isEmpty();
    }

    public boolean hasMembersToDelete() {
        return !CollectionUtils.isEmpty(deleteTargetMemberIds);
    }
}
