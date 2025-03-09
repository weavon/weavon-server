package coz.weavon.core.user.application.model.result;

import coz.weavon.core.user.domain.model.Users;
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
