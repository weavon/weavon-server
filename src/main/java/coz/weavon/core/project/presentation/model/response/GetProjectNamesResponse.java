package coz.weavon.core.project.presentation.model.response;

import coz.weavon.core.project.domain.model.Project;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProjectNamesResponse {

    private Long projectId;

    private String projectName;

    public static GetProjectNamesResponse fromResult(Project project) {
        return GetProjectNamesResponse.builder()
                .projectId(project.getProjectId())
                .projectName(project.getProjectName())
                .build();
    }
}
