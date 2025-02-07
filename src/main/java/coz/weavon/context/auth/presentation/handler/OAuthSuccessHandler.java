package coz.weavon.context.auth.presentation.handler;

import coz.weavon.context.auth.domain.model.AuthToken;
import coz.weavon.context.auth.domain.model.AuthUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${client.base-url}")
    private String clientBaseUrl;

    @Value("${auth.cookie.secure}")
    private boolean cookieSecure;

    @Value("${auth.cookie.max-age}")
    private int cookieMaxAge;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        AuthToken authToken = authUser.toAuthToken();

        response.addCookie(this.generateAuthCookie(authToken));
        response.sendRedirect(clientBaseUrl);
    }

    private Cookie generateAuthCookie(AuthToken authToken) {
        Cookie cookie = new Cookie("Authorization", authToken.getValue());

        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(cookieSecure);
        cookie.setMaxAge(cookieMaxAge);

        return cookie;
    }
}
