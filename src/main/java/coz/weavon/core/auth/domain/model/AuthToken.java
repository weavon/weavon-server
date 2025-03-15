package coz.weavon.core.auth.domain.model;

import coz.weavon.core.shared.domain.model.Property;
import coz.weavon.core.auth.domain.service.AuthTokenExtractor;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthToken {

    @Property(unique = true, nullable = false)
    private String value;

    public static AuthToken of(String value) {
        return AuthToken.builder().value(value).build();
    }

    public AuthUser toAuthUser() {
        return AuthTokenExtractor.extractAuthUser(this);
    }

    public boolean isValid() {
        return isNotExpired();
    }

    public boolean isExpired() {
        Date expiredDate = AuthTokenExtractor.extractExpiration(this);
        return expiredDate.before(new Date());
    }

    public boolean isNotExpired() {
        return !isExpired();
    }
}
