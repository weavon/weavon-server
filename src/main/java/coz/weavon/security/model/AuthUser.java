package coz.weavon.security.model;

import coz.weavon.core.user.domain.model.User;
import coz.weavon.security.helper.AuthTokenGenerator;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser implements UserDetails, OAuth2User {

    private Long userId;

    private String username;

    private String password;

    private String role;

    public static AuthUser of(Long userId, String username, String password, String role) {
        return AuthUser.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .role(role)
                .build();
    }

    public static AuthUser ofUser(User user) {
        return AuthUser.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRoleName())
                .build();
    }

    public AuthToken toAuthToken() {
        return AuthTokenGenerator.generateAuthToken(this);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> role);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }
}
