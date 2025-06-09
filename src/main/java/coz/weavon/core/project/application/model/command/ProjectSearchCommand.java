package coz.weavon.core.project.application.model.command;

import coz.weavon.core.project.domain.model.ProjectMemberRole;
import coz.weavon.core.shared.application.model.command.RestCommand;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

@Getter
@Builder
public class ProjectSearchCommand extends RestCommand {

    private List<Long> projectIds;

    private String projectName;

    private List<Long> memberIds;

    private List<ProjectMemberRole> memberRoles;

    public static ProjectSearchCommand ofMemberId(Long memberId) {
        return ProjectSearchCommand.builder().memberIds(List.of(memberId)).build();
    }

    @Override
    public void validate() {}

    public boolean hasMemberRoles() {
        return !CollectionUtils.isEmpty(memberRoles);
    }

    public List<ProjectMemberRole> getMemberRolesList() {
        return hasMemberRoles() ? memberRoles : Collections.EMPTY_LIST;
    }
}
