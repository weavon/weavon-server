package coz.weavon.context.member.domain.model;

import coz.weavon.common.domain.model.Property;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Member {

    @Property(unique = true, nullable = false, updatable = false)
    private Long memberId;

    @Property(unique = true, nullable = false, updatable = false)
    private String username;

    @Property(nullable = false)
    private String nickname;

    @Property(unique = true)
    private String email;

    @Property(nullable = false)
    private Role role;

    public static Member ofUser(String username, String nickname, String email) {
        return Member.builder()
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
