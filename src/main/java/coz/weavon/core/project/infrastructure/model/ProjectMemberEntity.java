package coz.weavon.core.project.infrastructure.model;

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
}
