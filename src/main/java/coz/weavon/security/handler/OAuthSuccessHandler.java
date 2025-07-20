package coz.weavon.security.handler;

import coz.weavon.constant.Message;
import coz.weavon.helper.MessageTranslator;
import coz.weavon.security.model.AuthToken;
import coz.weavon.security.model.AuthUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value(("${client.base-url}"))
    private String clientBaseUrl;

    @Value("${auth.cookie.secure}")
    private boolean secureCookie;

    @Value("${auth.jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    private final MessageTranslator messageTranslator;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        AuthToken authToken = authUser.toAuthToken();
        response.setHeader("Authorization", "Bearer " + authToken.getAccessToken());
        response.sendRedirect(clientBaseUrl);

        Cookie refreshTokenCookie = new Cookie("REFRESH_TOKEN", authToken.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(refreshTokenExpirationMinutes * 60);
        refreshTokenCookie.setSecure(secureCookie);

        log.info(messageTranslator.translate(Message.Authentication.USER_LOGGED_IN, authUser.getUsername()));
    }
}
