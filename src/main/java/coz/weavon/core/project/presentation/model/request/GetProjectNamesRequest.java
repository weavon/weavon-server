package coz.weavon.core.project.presentation.model.request;

import coz.weavon.core.project.application.model.command.ProjectSearchCommand;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetProjectNamesRequest {

    private Long userId;

    public ProjectSearchCommand toCommand(Long authUserId) {
        return ProjectSearchCommand.ofMemberId(Objects.nonNull(userId) ? userId : authUserId);
    }
}
