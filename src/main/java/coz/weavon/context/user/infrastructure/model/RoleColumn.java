package coz.weavon.context.user.infrastructure.model;

import coz.weavon.context.user.domain.model.Role;

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
