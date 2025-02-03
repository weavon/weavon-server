package coz.weavon.context.auth.domain.model;

import coz.weavon.common.domain.model.Property;
import coz.weavon.context.auth.domain.service.AuthTokenResolver;
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
        return AuthTokenResolver.resolveAuthUser(this);
    }

    public boolean isExpired() {
        Date expiredDate = AuthTokenResolver.resolveExpiration(this);
        return expiredDate.before(new Date());
    }
}
