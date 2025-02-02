package coz.weavon.context.security.domain.model;

import coz.weavon.common.domain.model.Property;
import coz.weavon.context.security.domain.service.AuthTokenResolver;
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

    public boolean isValid() {
        Date expiredDate = this.getExpiration();
        return expiredDate.after(new Date());
    }

    public String getUsername() {
        return AuthTokenResolver.resolveUsername(this);
    }

    public Date getExpiration() {
        return AuthTokenResolver.resolveExpiration(this);
    }

    public String getRole() {
        return AuthTokenResolver.resolveRole(this);
    }
}
