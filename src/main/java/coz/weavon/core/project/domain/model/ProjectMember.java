package coz.weavon.core.project.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectMember {

    private Long projectId;

    private Long memberId;

    private ProjectMemberRole memberRole;
}
