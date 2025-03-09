package coz.weavon.core.user.domain.model;

import coz.weavon.common.domain.model.Property;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    @Property(unique = true, nullable = false, updatable = false)
    private Long userId;

    @Property(unique = true, nullable = false, updatable = false)
    private String username;

    @Property
    private String password;

    @Property(nullable = false)
    private String nickname;

    @Property(unique = true)
    private String email;

    @Property(nullable = false)
    private Role role;

    public static User ofUser(String username, String nickname, String email) {
        return User.builder()
                .username(username)
                .nickname(nickname)
                .email(email)
                .role(Role.USER)
                .build();
    }

    public String getRoleName() {
        return role.name();
    }
}
