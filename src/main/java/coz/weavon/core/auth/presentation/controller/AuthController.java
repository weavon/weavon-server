package coz.weavon.core.auth.presentation.controller;

import coz.weavon.common.io.RestResponse;
import coz.weavon.constant.Message;
import coz.weavon.core.auth.application.service.AuthService;
import coz.weavon.core.auth.domain.model.AuthUser;
import coz.weavon.core.auth.presentation.model.request.AuthJoinRequest;
import coz.weavon.core.auth.presentation.model.response.AuthValidResponse;
import coz.weavon.helper.MessageTranslator;
import coz.weavon.util.AuthUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final MessageTranslator translator;

    private final PasswordEncoder passwordEncoder;

    private final AuthService authService;

    @GetMapping("/valid")
    public RestResponse<AuthValidResponse> getAuthValid() {
        AuthUser authUser = AuthUtils.getAuthUser();
        return RestResponse.of(AuthValidResponse.of(authUser.getUsername()));
    }

    @GetMapping("/refresh")
    public RestResponse<Boolean> refreshAccessToken() {
        return RestResponse.of(true);
    }

    @PostMapping("/join")
    public RestResponse<String> postAuthJoin(@Valid @RequestBody AuthJoinRequest request) {
        String username = request.getUsername();
        String password = passwordEncoder.encode(request.getPassword());

        authService.saveAuthUser(username, password);

        return RestResponse.of(translator.translate(Message.Authentication.JOIN_SUCCESS, username));
    }
}
