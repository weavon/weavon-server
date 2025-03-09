package coz.weavon.core.user.infrastructure.model;

import coz.weavon.core.user.domain.model.Role;

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
