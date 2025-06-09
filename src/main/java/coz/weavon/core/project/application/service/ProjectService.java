package coz.weavon.core.project.application.service;

import coz.weavon.core.project.application.model.command.ProjectSearchCommand;
import coz.weavon.core.project.domain.model.Projects;

public interface ProjectService {

    Projects searchProjects(ProjectSearchCommand command);
}
