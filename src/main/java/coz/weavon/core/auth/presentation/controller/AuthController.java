package coz.weavon.core.auth.presentation.controller;

import coz.weavon.common.io.RestResponse;
import coz.weavon.core.auth.domain.model.AuthUser;
import coz.weavon.core.auth.presentation.model.response.AuthValidResponse;
import coz.weavon.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/valid")
    public RestResponse<AuthValidResponse> getAuthValid() {
        AuthUser authUser = AuthUtils.getAuthUser();
        return RestResponse.of(AuthValidResponse.of(authUser.getUsername()));
    }

    @GetMapping("/refresh")
    public RestResponse<Boolean> refreshAccessToken() {
        return RestResponse.of(true);
    }
}
