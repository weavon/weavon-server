package coz.weavon.context.member.infrastructure.model;

import coz.weavon.context.member.domain.model.Role;

public enum RoleColumn {
    ADMIN,
    USER;

    public Role toDomain() {
        return switch (this) {
            case ADMIN -> Role.ADMIN;
            case USER -> Role.USER;
        };
    }
}
