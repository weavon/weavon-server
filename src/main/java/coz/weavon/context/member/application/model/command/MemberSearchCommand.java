package coz.weavon.context.member.application.model.command;

import coz.weavon.common.application.model.command.RestCommand;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSearchCommand extends RestCommand {

    private List<Long> memberIds;

    private String username;

    @Override
    public void validate() {}
}
