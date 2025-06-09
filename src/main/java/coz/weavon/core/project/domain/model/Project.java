package coz.weavon.core.project.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Project {

    private Long projectId;

    private String projectName;

    private List<ProjectMember> projectMembers;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
