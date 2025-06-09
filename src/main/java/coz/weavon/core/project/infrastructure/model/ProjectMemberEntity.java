package coz.weavon.core.project.infrastructure.model;

import coz.weavon.core.project.domain.model.ProjectMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_member", schema = "weavon")
public class ProjectMemberEntity {

    @EmbeddedId
    private ProjectMemberId id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ProjectMemberRoleColumn role = ProjectMemberRoleColumn.MEMBER;

    public ProjectMember toDomain() {
        return ProjectMember.builder()
                .projectId(id.getProjectId())
                .memberId(id.getMemberId())
                .memberRole(role.toDomain())
                .build();
    }
}
