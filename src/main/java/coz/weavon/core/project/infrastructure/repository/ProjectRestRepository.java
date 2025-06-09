package coz.weavon.core.project.infrastructure.repository;

import coz.weavon.core.project.application.model.command.ProjectSearchCommand;
import coz.weavon.core.project.application.repository.ProjectRepository;
import coz.weavon.core.project.domain.model.Projects;
import coz.weavon.core.project.infrastructure.model.ProjectEntity;
import coz.weavon.core.project.infrastructure.model.ProjectMemberRoleColumn;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProjectRestRepository implements ProjectRepository {

    private final ProjectQueryRepository queryRepository;

    @Override
    public Projects findProjects(ProjectSearchCommand command) {
        List<ProjectEntity> foundProjects = queryRepository.findProjects(
                command.getProjectIds(),
                command.getProjectName(),
                command.getMemberIds(),
                command.getMemberRolesList().stream()
                        .map(ProjectMemberRoleColumn::fromDomain)
                        .toList());

        return Projects.of(foundProjects.stream().map(ProjectEntity::toDomain).toList());
    }
}
