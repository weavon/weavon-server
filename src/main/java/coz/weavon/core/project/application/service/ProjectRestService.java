package coz.weavon.core.project.application.service;

import coz.weavon.core.project.application.model.command.ProjectSearchCommand;
import coz.weavon.core.project.application.repository.ProjectRepository;
import coz.weavon.core.project.domain.model.Projects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectRestService implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Projects searchProjects(ProjectSearchCommand command) {
        command.validate();

        return null;
    }
}
