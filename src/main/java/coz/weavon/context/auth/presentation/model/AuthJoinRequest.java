package coz.weavon.context.auth.presentation.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthJoinRequest {

    @Min(5)
    @Max(20)
    @NotBlank
    private String username;

    @Min(5)
    @Max(20)
    @NotBlank
    private String password;
}
