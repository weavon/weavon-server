package coz.weavon.core.project.infrastructure.model;

import coz.weavon.core.project.domain.model.Project;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project", schema = "weavon")
public class ProjectEntity {

    @Id
    @Column(name = "project_id", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "id.projectId")
    private List<ProjectMemberEntity> projectMembers;

    @Builder.Default
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Project toDomain() {
        return Project.builder()
                .projectId(projectId)
                .projectName(name)
                .projectMembers(projectMembers.stream()
                        .map(ProjectMemberEntity::toDomain)
                        .toList())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
