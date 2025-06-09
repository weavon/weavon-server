package coz.weavon.core.project.application.model.command;

import coz.weavon.core.project.domain.model.ProjectMemberRole;
import coz.weavon.core.shared.application.model.command.RestCommand;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectSearchCommand extends RestCommand {

    private List<Long> projectIds;

    private String projectName;

    private List<Long> memberIds;

    private List<ProjectMemberRole> memberRoles;

    @Override
    public void validate() {}
}
