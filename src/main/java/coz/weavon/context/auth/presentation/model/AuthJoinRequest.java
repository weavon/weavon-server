package coz.weavon.context.auth.presentation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthJoinRequest {

    private String username;

    private String password;
}
