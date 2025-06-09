package coz.weavon.core.project.infrastructure.model;

import coz.weavon.core.project.domain.model.ProjectMemberRole;

public enum ProjectMemberRoleColumn {
    LEADER,
    MANAGER,
    MEMBER;

    public static ProjectMemberRoleColumn fromDomain(ProjectMemberRole domain) {
        return switch (domain) {
            case ProjectMemberRole.LEADER -> LEADER;
            case ProjectMemberRole.MANAGER -> MANAGER;
            case ProjectMemberRole.MEMBER -> MEMBER;
        };
    }

    public ProjectMemberRole toDomain() {
        return switch (this) {
            case LEADER -> ProjectMemberRole.LEADER;
            case MANAGER -> ProjectMemberRole.MANAGER;
            case MEMBER -> ProjectMemberRole.MEMBER;
        };
    }
}
