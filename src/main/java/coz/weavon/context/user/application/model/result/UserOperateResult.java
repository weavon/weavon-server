package coz.weavon.context.user.application.model.result;

import coz.weavon.context.user.domain.model.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOperateResult {

    private Users createdUsers;

    private Users updatedUsers;
}
