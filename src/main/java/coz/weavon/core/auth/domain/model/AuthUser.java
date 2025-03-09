package coz.weavon.core.auth.domain.model;

import coz.weavon.common.domain.model.Property;
import coz.weavon.core.auth.domain.service.AuthTokenGenerator;
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

    @Property(unique = true, nullable = false, updatable = false)
    private String username;

    @Property
    private String password;

    @Property(nullable = false)
    private String role;

    public static AuthUser of(String username, String password, String role) {
        return AuthUser.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
    }

    public AuthToken toAuthToken() {
        return AuthTokenGenerator.generateAuthToken(username, password, role);
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
