package coz.weavon.core.project.presentation.controller;

import coz.weavon.common.io.RestResponse;
import coz.weavon.core.project.application.model.command.ProjectSearchCommand;
import coz.weavon.core.project.application.service.ProjectService;
import coz.weavon.core.project.domain.model.Projects;
import coz.weavon.core.project.presentation.model.request.GetProjectNamesRequest;
import coz.weavon.core.project.presentation.model.response.GetProjectNamesResponse;
import coz.weavon.security.model.AuthUser;
import coz.weavon.util.AuthUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/names")
    public RestResponse<List<GetProjectNamesResponse>> getProjectNames(GetProjectNamesRequest request) {
        AuthUser authUser = AuthUtils.getAuthUser();

        ProjectSearchCommand command = request.toCommand(authUser.getUserId());
        Projects projects = projectService.searchProjects(command);

        List<GetProjectNamesResponse> response = projects.value().stream()
                .map(GetProjectNamesResponse::fromResult)
                .toList();
        return RestResponse.of(response);
    }
}
