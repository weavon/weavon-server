package coz.weavon.core.user.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private Long userId;

    private String username;

    private String password;

    private String nickname;

    private String email;

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
