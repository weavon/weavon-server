package coz.weavon.core.auth.presentation.handler;

import coz.weavon.common.application.service.MessageTranslator;
import coz.weavon.core.auth.domain.model.AuthToken;
import coz.weavon.core.auth.domain.model.AuthUser;
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

    private static final String MSG_AUTH_USER_LOGGED_IN = "message.authentication.user.loggedIn";

    private final MessageTranslator messageTranslator;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        AuthToken authToken = authUser.toAuthToken();
        response.setHeader("Authorization", "Bearer " + authToken.getValue());
        response.sendRedirect(clientBaseUrl);

        log.info(messageTranslator.translate(MSG_AUTH_USER_LOGGED_IN, authUser.getUsername()));
    }
}
