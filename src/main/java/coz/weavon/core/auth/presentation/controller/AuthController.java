package coz.weavon.core.auth.presentation.controller;

import coz.weavon.core.auth.application.service.AuthService;
import coz.weavon.core.auth.domain.model.AuthUser;
import coz.weavon.core.auth.presentation.model.AuthJoinRequest;
import coz.weavon.core.auth.presentation.model.AuthValidResponse;
import coz.weavon.core.shared.presentation.model.response.RestResponse;
import coz.weavon.helper.MessageTranslator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private static final String MSG_AUTH_JOIN_SUCCESS = "message.authentication.join.success";

    private final MessageTranslator translator;

    private final PasswordEncoder passwordEncoder;

    private final AuthService authService;

    @GetMapping("/valid")
    public RestResponse<AuthValidResponse> getAuthValid() {
        AuthUser authUser = (AuthUser)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return RestResponse.of(AuthValidResponse.of(authUser));
    }

    @PostMapping("/join")
    public RestResponse<String> postAuthJoin(@Valid @RequestBody AuthJoinRequest request) {
        String username = request.getUsername();
        String password = passwordEncoder.encode(request.getPassword());

        authService.saveAuthUser(username, password);

        return RestResponse.of(translator.translate(MSG_AUTH_JOIN_SUCCESS, username));
    }
}
