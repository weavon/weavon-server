package coz.weavon.core.user.infrastructure.model;

import coz.weavon.core.user.domain.model.User;
import coz.weavon.core.user.domain.model.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER", schema = "WEAVON")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false, updatable = false)
    private Long userId;

    @Column(name = "username", unique = true, nullable = false, updatable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleColumn role;

    public static UserEntity fromDomain(User user) {
        return UserEntity.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .role(RoleColumn.USER)
                .build();
    }

    public static List<UserEntity> fromDomains(Users users) {
        return users.getValue().stream().map(UserEntity::fromDomain).toList();
    }

    public User toDomain() {
        return User.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .role(role.toDomain())
                .build();
    }

    public void update(UserEntity userEntity) {
        password = Optional.ofNullable(userEntity.getPassword()).orElse(password);
        nickname = Optional.ofNullable(userEntity.getNickname()).orElse(nickname);
        email = Optional.ofNullable(userEntity.getEmail()).orElse(email);
        role = Optional.of(userEntity.getRole()).orElse(role);
    }
}
