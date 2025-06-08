package coz.weavon.core.project.domain.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Project {

    private Long projectId;

    private String projectName;

    private Long memberId;

    private ProjectMemberRole memberRole;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
