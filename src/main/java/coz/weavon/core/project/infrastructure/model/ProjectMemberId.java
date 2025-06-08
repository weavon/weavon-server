package coz.weavon.core.project.infrastructure.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProjectMemberId implements Serializable {

    @Column(name = "project_id", unique = true, nullable = false, updatable = false)
    private Long projectId;

    @Column(name = "user_id", unique = true, nullable = false, updatable = false)
    private Long memberId;
}
