package coz.weavon.context.auth.domain.model;

import coz.weavon.common.domain.model.Property;
import coz.weavon.context.auth.domain.service.AuthTokenGenerator;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Data
@Builder
public class AuthUser implements OAuth2User {

    @Property(unique = true, nullable = false, updatable = false)
    private String username;

    @Property(nullable = false)
    private String role;

    public static AuthUser of(String username, String role) {
        return AuthUser.builder().username(username).role(role).build();
    }

    public AuthToken toAuthToken() {
        return AuthTokenGenerator.generateAuthToken(this.getUsername(), this.getRole());
    }

    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> this.role);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }
}
