package coz.weavon.core.project.application.repository;

import coz.weavon.core.project.application.model.command.ProjectSearchCommand;
import coz.weavon.core.project.domain.model.Projects;

public interface ProjectRepository {

    Projects findProjects(ProjectSearchCommand command);
}
