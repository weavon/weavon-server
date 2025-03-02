package coz.weavon.context.user.presentation.controller;

import coz.weavon.common.presentation.model.response.RestResponse;
import coz.weavon.context.user.application.model.command.UserSearchCommand;
import coz.weavon.context.user.application.service.UserService;
import coz.weavon.context.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/username/{username}/exists")
    public RestResponse<Boolean> hasUsername(
            @PathVariable("username") String username
    ) {
        UserSearchCommand userSearchCommand = UserSearchCommand.ofUsername(username);
        Optional<User> optionalUser = userService.searchOptionalUser(userSearchCommand);
        return RestResponse.of(optionalUser.isPresent());
    }
}
