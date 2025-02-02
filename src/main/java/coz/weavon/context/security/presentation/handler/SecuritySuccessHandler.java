package coz.weavon.context.security.presentation.handler;

import coz.weavon.context.security.application.service.AuthTokenService;
import coz.weavon.context.security.domain.model.AuthToken;
import coz.weavon.context.security.domain.model.AuthUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecuritySuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${auth.cookie.secure}")
    private boolean cookieSecure;

    @Value("${auth.cookie.max-age}")
    private int cookieMaxAge;

    private final AuthTokenService authTokenService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        AuthToken authToken = authTokenService.getAuthTokenByAuthUser(authUser);
        Cookie cookie = this.generateAuthCookie(authToken);
        response.addCookie(cookie);
        response.sendRedirect("http://localhost:3000/");
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
